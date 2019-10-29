package com.example.sell.controller;


import com.example.sell.dataobject.OrderMaster;
import com.example.sell.enums.RestEnum;
import com.example.sell.exception.SellException;
import com.example.sell.service.OrderService;
import com.example.sell.service.PayService;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/** 支付
 * @Author: baiyj
 * @Date: 2019/10/18
 */

@Controller
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PayService payService;

    @RequestMapping("/create")
    public String create(@RequestParam(name = "orderId") String orderId,
                         @RequestParam(name = "returnUrl") String returnUrl,
                         Model model) {
        // 查询订单
        OrderMaster orderMaster = orderService.findOne(orderId);
        if (orderMaster == null){
            throw new SellException(RestEnum.ORDER_NOT_EXIST);
        }
        //发起支付
        PayResponse payResponse = payService.create(orderMaster);
        model.addAttribute("payResponse",payResponse);
        model.addAttribute("returnUrl",returnUrl);
        return "pay/create";
    }

    /**
     * 微信支付异步通知地址 携带着支付后响应回来的数据
     * 支付结果状态更改
     *@return
     */
    @PostMapping("/notify")
    public String notify(@RequestBody String notifyData){
        payService.notify(notifyData);


        // 返回给微信处理结果
        return "pay/success";
    }

}
