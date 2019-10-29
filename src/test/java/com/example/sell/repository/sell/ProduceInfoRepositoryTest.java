package com.example.sell.repository.sell;

import com.example.sell.dataobject.ProductInfo;
import com.example.sell.repository.ProductInfoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProduceInfoRepositoryTest {

    @Autowired
    private ProductInfoRepository repository;

    @Test
    public void findByProductStatus() {
        List<ProductInfo> byProductStatus = repository.findByProductStatus(0);
        for (ProductInfo productStatus : byProductStatus) {
            System.out.println(productStatus);
        }
    }

    @Test
    public void save(){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123");
        productInfo.setCategoryType(2);
        productInfo.setProductDescription("很好喝的粥");
        productInfo.setProductName("皮蛋粥");
        productInfo.setProductIcon("http://xxx.jpg");
        productInfo.setProductPrice(new BigDecimal(3.2));
        productInfo.setProductStock(100);
        productInfo.setProductStatus(0);
        repository.save(productInfo);
    }

}