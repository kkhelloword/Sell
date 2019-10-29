package com.example.sell.service.impl;

import com.example.sell.dataobject.OrderMaster;
import com.example.sell.service.PayService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PayServiceImplTest {

    @Autowired
    private PayService payService;

    @Test
    public void create() {
        // 支付测试
        OrderMaster master = new OrderMaster();
        payService.create(master);
    }
}