package com.example.sell.service.impl;

import com.example.sell.dataobject.SellerInfo;
import com.example.sell.repository.SellerInfoRepository;
import com.example.sell.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerInfoRepository infoRepository;

    @Override
    public SellerInfo findByOpenid(String openid) {
        return infoRepository.findByOpenid(openid);
    }
}
