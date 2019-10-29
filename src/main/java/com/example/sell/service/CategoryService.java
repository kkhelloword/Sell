package com.example.sell.service;

import com.example.sell.dataobject.ProductCategory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    ProductCategory findOne(Integer categoryid);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    ProductCategory save(ProductCategory productCategory);
}
