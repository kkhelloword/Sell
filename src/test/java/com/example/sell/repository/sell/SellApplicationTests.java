package com.example.sell.repository.sell;

import com.example.sell.dataobject.ProductCategory;
import com.example.sell.repository.ProductCategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SellApplicationTests {

    @Autowired
    private ProductCategoryRepository repository;

    @Test
    public void contextLoads() {
        ProductCategory productCategory = repository.findById(1).orElse(null);
        System.out.println(productCategory);
    }
    @Test
    public void Test() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryId(2);
        productCategory.setCategoryName("女生最爱");
        productCategory.setCategoryType(3);
        repository.save(productCategory);
    }

    @Test
    @Transactional
    public void Test2() {
//        先查询再修改会出现时间无法正常更新的问题
        ProductCategory productCategory = repository.findById(2).orElse(null);
        productCategory.setCategoryType(7);
        repository.save(productCategory);
    }

    @Test
    public void Test3() {
        List<Integer> ids = Arrays.asList(2,3);
        List<ProductCategory> byCategoryTypeIn = repository.findByCategoryTypeIn(ids);
//       断言判断集合是否为null
        Assert.assertNotEquals(0,byCategoryTypeIn.size());

    }
}
