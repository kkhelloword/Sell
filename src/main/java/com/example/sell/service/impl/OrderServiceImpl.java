package com.example.sell.service.impl;

import com.example.sell.dataobject.OrderDetail;
import com.example.sell.dataobject.OrderMaster;
import com.example.sell.dataobject.ProductInfo;
import com.example.sell.dto.CartDto;
import com.example.sell.enums.OrderStatusEnum;
import com.example.sell.enums.PayStatusEnum;
import com.example.sell.enums.RestEnum;
import com.example.sell.exception.ResponseBankException;
import com.example.sell.exception.SellException;
import com.example.sell.repository.OrderDetailsRepository;
import com.example.sell.repository.OrderMasterRepository;
import com.example.sell.repository.ProductInfoRepository;
import com.example.sell.service.*;
import com.example.sell.utils.KeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.pl.PESEL;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductInfoRepository infoRepository;

    @Autowired
    private OrderMasterRepository masterRepository;

    @Autowired
    private OrderDetailsRepository detailsRepository;

    @Autowired
    private PayService payService;

    @Autowired
    private PushMessage pushMessage;

    @Autowired
    private WebSocket webSocket;

    @Override
    @Transactional
    public OrderMaster create(OrderMaster orderMaster) {
         String orderid = KeyUtils.getUniqueKey();
        // 查询商品信息
        List<OrderDetail> orderDetails = orderMaster.getOrderDetails();
        BigDecimal bigDecimal = new BigDecimal(BigInteger.ZERO);
        OrderMaster orderdto = new OrderMaster();
        for (OrderDetail orderDetail : orderDetails) {
            ProductInfo productInfo = infoRepository.findById(orderDetail.getProductId()).orElse(null);
            if (productInfo == null){
                throw new SellException(RestEnum.PRODUCT_NO_EXIST);
//                throw new ResponseBankException();
            }
            // 计算总价
            bigDecimal = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(bigDecimal);
            //写入订单数据库（orderMaster 和 orderDetails）
            orderDetail.setDetailId(KeyUtils.getUniqueKey());
            orderDetail.setOrderId(orderid);
            BeanUtils.copyProperties(productInfo,orderDetail);
            detailsRepository.save(orderDetail);
        }
        BeanUtils.copyProperties(orderMaster,orderdto);
        orderdto.setOrderId(orderid);
        orderdto.setOrderAmount(bigDecimal);
        orderdto.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderdto.setPayStatus(PayStatusEnum.WAIT.getCode());
        masterRepository.save(orderdto);
        // 减库存
        List<CartDto> cartDtoList = orderMaster.getOrderDetails().stream().map(e ->
                new CartDto(e.getProductId(), e.getProductQuantity())
        ).collect(Collectors.toList());
        productService.decreaseStock(cartDtoList);

        // 发送websocket消息
        webSocket.sendMessage(orderid);
        return orderdto;
    }

    @Override
    public OrderMaster findOne(String orderId) {
        OrderMaster orderMaster = masterRepository.findById(orderId).orElse(null);
        if (orderMaster == null){
            throw new SellException(RestEnum.ORDER_NOT_EXIST);
        }
        // 根据订单编号查询订单详情
        List<OrderDetail> detailList = detailsRepository.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(detailList)){
            throw new SellException(RestEnum.ORDER_NOT_EXIST);
        }
        orderMaster.setOrderDetails(detailList);
        return orderMaster;

    }

    @Override
    public Page<OrderMaster> findList(String buyerOpenId, Pageable pageable) {
        return masterRepository.findByBuyerOpenid(buyerOpenId, pageable);
    }

    @Override
    @Transactional
    public OrderMaster cancel(OrderMaster orderMaster) {
        //判断订单状态 只有新订单才能修改状态
        if (!orderMaster.getOrderStatus().getCode().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【取消订单】 订单状态不正确,orderId={},orderStatus={}",orderMaster.getOrderId(),orderMaster.getOrderStatus());
            throw new SellException(RestEnum.ORDER_STATUS_ERROR);
        }
        // 修改订单状态
        orderMaster.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        OrderMaster save = masterRepository.save(orderMaster);
        if (save == null){
            log.error("【取消订单】更新失败,orderId={},orderStatus={}",orderMaster.getOrderId(),orderMaster.getOrderStatus());
            throw new SellException(RestEnum.ORDER_UPDATE_FAIL);
        }
        if (CollectionUtils.isEmpty(orderMaster.getOrderDetails())){
            log.error("【取消订单】订单中无商品详情,orderMaster",orderMaster);
            throw new SellException(RestEnum.ORDERDETAIL_NOT_EXIST);
        }
        List<CartDto> cartDtos = orderMaster.getOrderDetails().stream().map(e ->
                new CartDto(e.getProductId(), e.getProductQuantity())
        ).collect(Collectors.toList());
       // 加库存
        productService.increaseStock(cartDtos);
        //支付成功的话退款
        if (orderMaster.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())){
            payService.refund(orderMaster);
        }
        return orderMaster;
    }

    @Override
    public OrderMaster finish(OrderMaster orderMaster) {
        // 判断订单状态
        if (!orderMaster.getOrderStatus().getCode().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【完结订单】订单状态不正确 ordermaster={}",orderMaster);
            throw new SellException(RestEnum.ORDER_STATUS_ERROR);
        }
        // 修改订单状态
        orderMaster.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster save = masterRepository.save(orderMaster);
        if (save == null){
            log.error("【完结订单】订单更新失败.orderMaster={}",orderMaster);
            throw new SellException(RestEnum.ORDER_UPDATE_FAIL);
        }
        /*发送模板消息*/
        pushMessage.orderStatus(orderMaster);
        return orderMaster;
    }

    @Override
    public OrderMaster paid(OrderMaster orderMaster) {
        // 判断订单状态
        if (!orderMaster.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【支付订单】订单状态错误,orderMaster={}",orderMaster);
            throw new SellException(RestEnum.ORDER_STATUS_ERROR);
        }
        // 判断支付状态
        if (!orderMaster.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            log.error("【支付订单】支付状态错误,orderMaster={}",orderMaster);
            throw new SellException(RestEnum.PAY_STATUS_ERROR);
        }
        // 修改支付状态
        orderMaster.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster save = masterRepository.save(orderMaster);
        if (save == null){
            throw new SellException(RestEnum.ORDER_UPDATE_FAIL);
        }
        return orderMaster;
    }

    @Override
    public Page<OrderMaster> findList(Pageable pageable) {
        return masterRepository.findAll(pageable);
    }
}
