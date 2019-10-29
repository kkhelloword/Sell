package com.example.sell.service;


import com.example.sell.dataobject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    /*创建订单*/
    OrderMaster  create(OrderMaster orderMaster);
     /*查询单个订单*/
    OrderMaster findOne(String orderId);
    /*查询当前微信号的订单列表*/
    Page<OrderMaster> findList(String buyerOpenId, Pageable pageable);
    /*取消订单*/
    OrderMaster cancel(OrderMaster orderMaster);
    /*完结订单*/
    OrderMaster finish(OrderMaster orderMaster);
    /*支付订单*/
    OrderMaster paid(OrderMaster orderMaster);

    /*卖家系统*/
    /*分页查询所有的订单*/
    Page<OrderMaster> findList(Pageable pageable);
}
