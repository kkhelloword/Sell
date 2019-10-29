package com.example.sell.dto;

import lombok.Data;

/**
 * 购物车
 *
 * @Author: baiyj
 * @Date: 2019/10/15
 */

@Data
public class CartDto {
    /*商品id*/
    private String productId;
    /*商品数量*/
    private Integer productQuantity;

    public CartDto(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
