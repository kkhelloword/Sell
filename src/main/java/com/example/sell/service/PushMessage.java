package com.example.sell.service;

import com.example.sell.dataobject.OrderMaster;

/**
 * 推送消息
 * @Author: baiyj
 * @Date: 2019/10/23
 */

public interface PushMessage {
    /**
    * 订单状态更新推送
    *@param  [orderMaster]
    *@return void
    */

    void  orderStatus(OrderMaster orderMaster);

}
