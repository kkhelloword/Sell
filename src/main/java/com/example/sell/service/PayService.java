package com.example.sell.service;

import com.example.sell.dataobject.OrderMaster;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;

/**
 * 支付
 * @Author: baiyj
 * @Date: 2019/10/18
 */
public interface PayService {
    PayResponse create(OrderMaster orderMaster);
    PayResponse notify(String notifyData);
    /*退款*/
    RefundResponse refund(OrderMaster orderMaster);
}
