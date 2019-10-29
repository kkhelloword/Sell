package com.example.sell.repository;

import com.example.sell.dataobject.SellerInfo;
import com.example.sell.service.SellerService;
import com.example.sell.utils.KeyUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.AssertTrue;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SellerInfoRepositoryTest {

    @Autowired
    private SellerInfoRepository repository;

    @Autowired
    private SellerService sellerService;

    private final static String OPENID = "123";
    @Test
    public void save() {
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setId(KeyUtils.getUniqueKey());
        sellerInfo.setOpenid("123");
        sellerInfo.setUsername("张三");
        sellerInfo.setPassword("123");
        repository.save(sellerInfo);

    }

    @Test
    public void findByOpenid(){
        SellerInfo byOpenid = repository.findByOpenid(OPENID);
        Assert.assertTrue("已经查到用户信息",byOpenid!=null);
    }
    @Test
    public void findByOpenidService(){
        SellerInfo byOpenid = sellerService.findByOpenid(OPENID);
        Assert.assertTrue("已经查到用户信息",byOpenid!=null);
    }
}