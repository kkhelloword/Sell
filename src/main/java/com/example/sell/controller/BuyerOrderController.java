package com.example.sell.controller;

import com.example.sell.VO.ResultVO;
import com.example.sell.convert.OrderForm2OrderMaster;
import com.example.sell.dataobject.OrderMaster;
import com.example.sell.enums.RestEnum;
import com.example.sell.exception.SellException;
import com.example.sell.form.OrderForm;
import com.example.sell.service.BuyerService;
import com.example.sell.service.OrderService;
import com.example.sell.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;

    // 创建订单
    @RequestMapping("create")
    public ResultVO<Map<String,String>> create(@Valid  OrderForm form,
                                               BindingResult bindResult){
        if (bindResult.hasErrors()){
            log.error("【创建订单】 请求参数不正确,orderForm={}",form);
            throw new SellException(RestEnum.PARAM_ERROR.getCode(),bindResult.getFieldError().getDefaultMessage());
        }
        OrderMaster orderMaster = OrderForm2OrderMaster.ConvertOrderMaster(form);
        if (CollectionUtils.isEmpty(orderMaster.getOrderDetails())){
            log.error("【创建订单】 购物车不能为空");
            throw new SellException(RestEnum.CART_EMPTY);
        }
        OrderMaster master = orderService.create(orderMaster);
        // 拼装数据
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("orderId",master.getOrderId());
        return ResultVOUtil.success(hashMap);
    }

    // 订单列表
    @GetMapping("/list")
    public ResultVO<List<OrderMaster>> getOrderList(@RequestParam(name = "openid")String openid,
                                              @RequestParam(name = "page")Integer page,
                                              @RequestParam(name = "size")Integer size){
        if (StringUtils.isEmpty(openid)){
            log.error("【查询订单列表】openid 为空",openid);
            throw new SellException(RestEnum.PARAM_ERROR);
        }
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<OrderMaster> list = orderService.findList(openid, pageRequest);
        return ResultVOUtil.success(list.getContent());
    }
    // 查询订单详情

    @GetMapping("/detail")
    public ResultVO<OrderMaster> detail(@RequestParam(name = "openid")String openid,
                                              @RequestParam(name = "orderId")String orderId){
        OrderMaster master = buyerService.findOrderOne(openid, orderId);
        return ResultVOUtil.success(master);
    }
    //取消订单
    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam(name = "openid")String openid,
                           @RequestParam(name = "orderId")String orderId){
        OrderMaster master = buyerService.cancelOrder(openid, orderId);
        orderService.cancel(master);
        return ResultVOUtil.success();
    }
}
