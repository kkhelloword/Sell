package com.example.sell.service;

import com.example.sell.dataobject.SellerInfo;

public interface SellerService {
    SellerInfo findByOpenid(String openid);
}
