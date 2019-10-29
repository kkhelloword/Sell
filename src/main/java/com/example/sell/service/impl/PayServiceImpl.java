package com.example.sell.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.sell.config.WeChatPayConfig;
import com.example.sell.dataobject.OrderMaster;
import com.example.sell.enums.RestEnum;
import com.example.sell.exception.SellException;
import com.example.sell.service.OrderService;
import com.example.sell.service.PayService;
import com.example.sell.utils.MathUtil;
import com.lly835.bestpay.config.WxPayH5Config;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class PayServiceImpl implements PayService {

    @Autowired
    private BestPayServiceImpl bestPayService;
    @Autowired
    private OrderService orderService;

    private final static String ORDER_NAME = "微信点餐订单";
    @Override
    public PayResponse create(OrderMaster orderMaster) {
        // 发起支付
        PayRequest payRequest = new PayRequest();
        payRequest.setOrderId(orderMaster.getOrderId());
        payRequest.setOrderAmount(orderMaster.getOrderAmount().doubleValue());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setOpenid(orderMaster.getBuyerOpenid());
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信支付】 支付请求payRequest={}",payRequest);
        PayResponse payResponse = bestPayService.pay(payRequest);
        log.info("【微信支付】 支付响应结果 payresponse={}",payResponse);
        return payResponse;
    }

    @Override
    public PayResponse notify(String notifyData) {
        // 1. 验证签名
        // 2. 支付的状态
        // 3. 验证支付的金额
        // 4. 支付人(下单人 == 支付人)

        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info("【微信支付】异步通知,payResponse={}", JSON.toJSONString(payResponse));

        // 查询订单
        OrderMaster master = orderService.findOne(payResponse.getOrderId());
        if (master == null){
            log.info("【微信支付】异步通知,订单不存在,orderId={}",payResponse.getOrderId());
            throw new SellException(RestEnum.ORDER_NOT_EXIST);
        }
        //判断金额是否一致
        if (MathUtil.comepare(master.getOrderAmount().doubleValue(),payResponse.getOrderAmount())){
            log.info("【微信支付】异步通知,金额不一致,orderId={},微信通知金额={},系统金额={}",
                    payResponse.getOrderId(),
                    payResponse.getOrderAmount(),
                    master.getOrderAmount());
            throw new SellException(RestEnum.WX_AMOUNT_VALIDATION_FAIL);
        }
        // 修改状态
        orderService.paid(master);
        return payResponse;
    }

    @Override
    public RefundResponse refund(OrderMaster orderMaster) {

        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderId(orderMaster.getOrderId());
        refundRequest.setOrderAmount(orderMaster.getOrderAmount().doubleValue());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        return bestPayService.refund(refundRequest);
    }

}
