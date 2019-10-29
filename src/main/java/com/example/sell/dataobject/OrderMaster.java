package com.example.sell.dataobject;


import com.example.sell.enums.OrderStatusEnum;
import com.example.sell.enums.PayStatusEnum;
import com.example.sell.utils.GetEnumUtils;
import com.example.sell.utils.serialize.Date2LongSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Data
@DynamicUpdate
@ToString
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderMaster {

    @Id
    private String orderId;

    private String buyerName;

    private String buyerPhone;

    /*买家地址*/
    private String buyerAddress;

    /*买家微信*/
    private String buyerOpenid;

    /*订单总金额*/
    private BigDecimal orderAmount;

    /*订单状态 默认为新下单*/
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();

    /*支付状态,默认为等待支付*/
    private Integer payStatus = PayStatusEnum.WAIT.getCode();

    /*创建时间*/
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    /*更新时间*/
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    @Transient
    private List<OrderDetail> orderDetails;

    @JsonIgnore
    public OrderStatusEnum getOrderStatus(){
      return GetEnumUtils.getEnumByCode(orderStatus,OrderStatusEnum.class);
    }

    @JsonIgnore
    public PayStatusEnum getPayStatus(){
        return GetEnumUtils.getEnumByCode(payStatus,PayStatusEnum.class);
    }
}
