package com.example.sell.controller;

import com.example.sell.dataobject.OrderMaster;
import com.example.sell.enums.RestEnum;
import com.example.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: baiyj
 * @Date: 2019/10/22
 */

@Controller
@RequestMapping("/seller/order")
@Slf4j
public class SellerOrderControllr {

    @Autowired
    private OrderService orderService;

    /**
     * 查询订单列表
     *
     * @param [page,size, model]
     * @return java.lang.String
     */

    @GetMapping("/list")
    public String findList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                           @RequestParam(value = "size", defaultValue = "10") Integer size,
                           Model model) {
        Page<OrderMaster> orderDtoPage = orderService.findList(PageRequest.of(page - 1, size));
        model.addAttribute("orderDtoPage", orderDtoPage);
        model.addAttribute("currentPage", page);
        return "/order/list";
    }


    @GetMapping("/cancel")
    public String cancel(@RequestParam(value = "orderId") String orderId,
                         Model model) {
        try {
            /*根据订单id查询订单对象*/
            OrderMaster orderMaster = orderService.findOne(orderId);
            /*取消订单*/
            orderService.cancel(orderMaster);
        } catch (Exception e) {
            model.addAttribute("msg", e.getMessage());
            model.addAttribute("url", "/sell/seller/order/list");
            return "/common/error";
        }
        model.addAttribute("msg", RestEnum.ORDER_CANCEL_SUCCESS.getMessage());
        model.addAttribute("url", "/sell/seller/order/list");
        return "/common/success";
    }

    /**
     * 订单详情
     *
     * @param [orderId, model]
     * @return java.lang.String
     */
    @GetMapping("/detail")
    public String detail(@RequestParam(value = "orderId") String orderId,
                         Model model) {
        OrderMaster master = new OrderMaster();
        try {
            master = orderService.findOne(orderId);
        } catch (Exception e) {
            log.error("【卖家端查询订单详情】 发送异常{}", e);
            model.addAttribute("msg", e.getMessage());
            model.addAttribute("url", "/sell/seller/order/list");
            return "/common/error";
        }
        model.addAttribute("orderDto", master);
        return "/order/detail";
    }

    /** 完结订单
     * @param [orderId, model]
     * @return java.lang.String
     */
    @GetMapping("/finish")
    public String finish(@RequestParam(value = "orderId") String orderId,
                         Model model) {
        try {
            /*根据订单id查询订单对象*/
            OrderMaster orderMaster = orderService.findOne(orderId);
            /*完结订单*/
            orderService.finish(orderMaster);
        } catch (Exception e) {
            model.addAttribute("msg", e.getMessage());
            model.addAttribute("url", "/sell/seller/order/list");
            return "/common/error";
        }
        model.addAttribute("msg", RestEnum.ORDER_FINISH_SUCCESS.getMessage());
        model.addAttribute("url", "/sell/seller/order/list");
        return "/common/success";
    }
}
