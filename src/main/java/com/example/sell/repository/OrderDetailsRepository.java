package com.example.sell.repository;

import com.example.sell.dataobject.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailsRepository extends JpaRepository<OrderDetail,String> {

    /*根据订单id查询订单详情*/
    List<OrderDetail> findByOrderId(String orderid);
}
