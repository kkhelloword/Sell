package com.example.sell.VO;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;

import java.util.List;

/** 
    * @Description: 返回的商品列表对象
        * @Author: baiyj
        * @Date: 2019/10/12
        */ 
@Data
public class ProductVO {

    @JsonProperty("name")
    private String categoryName;

    @JsonProperty("type")
    private Integer categoryType;

    @JsonProperty("foods")
    private List<ProductInfoVO> listFoods;

}
