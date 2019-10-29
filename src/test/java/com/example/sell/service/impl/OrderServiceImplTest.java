package com.example.sell.service.impl;

import com.example.sell.dataobject.OrderMaster;
import com.example.sell.service.OrderService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderServiceImplTest {
    @Autowired
    private OrderService orderService;

    @Test
    public void findList() {
        Page<OrderMaster> list = orderService.findList(PageRequest.of(0, 2));
        Assert.assertTrue("查询订单失败",list.getTotalElements() < 0);
    }
}