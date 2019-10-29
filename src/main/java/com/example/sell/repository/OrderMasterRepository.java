package com.example.sell.repository;

import com.example.sell.dataobject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMasterRepository  extends JpaRepository<OrderMaster,String> {

    /* 根据微信号分页查询订单*/
    Page<OrderMaster> findByBuyerOpenid(String openid, Pageable pageable);

}
