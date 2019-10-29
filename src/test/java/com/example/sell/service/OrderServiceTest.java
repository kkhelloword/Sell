package com.example.sell.service;

import com.example.sell.dataobject.OrderDetail;
import com.example.sell.dataobject.OrderMaster;
import com.example.sell.enums.OrderStatusEnum;
import com.example.sell.enums.PayStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class OrderServiceTest {
    @Autowired
    private OrderService orderService;

    private final String buyerOpenid = "110110";
    @Test
    public void create() {
        for (int i = 0; i < 20; i++) {
            OrderMaster orderMaster = new OrderMaster();
            orderMaster.setBuyerName("孙悟空");
            orderMaster.setBuyerAddress("花果山");
            orderMaster.setBuyerPhone("18868822111");
            orderMaster.setBuyerOpenid(buyerOpenid);
            // 购物车
            ArrayList<OrderDetail> cartDtos = new ArrayList<>();
            OrderDetail orderDetails = new OrderDetail();
            orderDetails.setProductId("123");
            orderDetails.setProductQuantity(2);

            cartDtos.add(orderDetails);
            orderMaster.setOrderDetails(cartDtos);
            orderService.create(orderMaster);
        }
    }

    @Test
    public void findOne() {
        OrderMaster one = orderService.findOne("1571118897512168040");
        System.out.println(one);
    }

    @Test
    public void findList() {
        Page<OrderMaster> list = orderService.findList(buyerOpenid, new PageRequest(0, 2));
        for (OrderMaster orderMaster : list) {
            System.out.println(orderMaster);
        }
    }

    @Test
    public void cancel() {
        OrderMaster one = orderService.findOne("1571118897512168040");
        orderService.cancel(one);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(),one.getOrderStatus());
    }

    @Test
    public void finish() {
        OrderMaster one = orderService.findOne("1571118897512168040");
        OrderMaster finish = orderService.finish(one);
        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(),finish.getOrderStatus());
    }

    @Test
    public void paid() {
        OrderMaster one = orderService.findOne("1571118897512168040");
        OrderMaster paid = orderService.paid(one);
        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(),paid.getPayStatus());
    }
}