package com.example.sell.service;

import com.example.sell.dataobject.ProductInfo;
import com.example.sell.dto.CartDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    ProductInfo findOne(String productId);

    /*查询所有在架商品列表*/
    List<ProductInfo> findUpAll();

    /*查询所有商品*/
    Page<ProductInfo> findAll(PageRequest pageRequest);

    /*保存商品*/
    ProductInfo save(ProductInfo productInfo);

    /*加库存*/
    void increaseStock(List<CartDto> cartDtoList);

    /*减库存*/
    void decreaseStock(List<CartDto> cartDtoList);

    /*上架*/
    ProductInfo onSale(String productId);

    /*下架*/
    ProductInfo offSale(String productId);


}
