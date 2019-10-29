package com.example.sell.dataobject;

import com.example.sell.enums.ProductStatusEnum;
import com.example.sell.utils.GetEnumUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
    * @Description: 商品表
        * @Author: baiyj
        * @Date: 2019/10/12
        */

@Data
@Entity
public class ProductInfo {

    @Id
    /*商品id*/
    private String productId;

    /*商品名字*/
    private String productName;

    /*商品价格*/
    private BigDecimal productPrice;

    /*库存*/
    private Integer productStock;

    /*商品描述*/
    private String productDescription;

    /*商品icon*/
    private String productIcon;

    /*商品状态 0正常 1下架*/
    private Integer productStatus;

    /*类目编号*/
    private Integer categoryType;

    /*创建时间*/
    private Date createTime;

    /*修改时间*/
    private Date updateTime;

    @JsonIgnore
    public ProductStatusEnum getProductStatus(){
        return GetEnumUtils.getEnumByCode(productStatus,ProductStatusEnum.class);
    }

}
