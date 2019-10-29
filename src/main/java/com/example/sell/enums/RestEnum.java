package com.example.sell.enums;

import lombok.Getter;

@Getter
public enum RestEnum {
    SUCCESS(0,"成功"),
    PARAM_ERROR(1,"请求参数不正确"),
    PRODUCT_NO_EXIST(10,"商品不存在"),
    PRODUCT_STOCK_ERROR(11,"库存不正确"),
    ORDER_NOT_EXIST(12,"订单不存在"),
    ORDERDETAIL_NOT_EXIST(13,"订单不存在"),
    ORDER_STATUS_ERROR(14,"订单状态错误"),
    ORDER_UPDATE_FAIL(15,"订单更新失败"),
    ORDER_DETAIL_EMPTY(16,"订单详情为空"),
    PAY_STATUS_ERROR(17,"支付状态错误"),
    CART_EMPTY(18,"购物车不能为空"),
    ORDER_OWNER_ERROR(19,"该订单不属于当前用户"),
    WX_AMOUNT_VALIDATION_FAIL(20,"微信支付异步通知金额效验不通过"),
    ORDER_CANCEL_SUCCESS(21,"订单取消成功"),
    ORDER_FINISH_SUCCESS(22,"订单完结成功"),
    PRODUCT_STATUS_ERROR(23,"商品状态错误"),
    ONSALE_SUCCESS(24,"上架成功"),
    OFFSELE_SUCCESS(25,"下架成功"),
    OPENID_NOTFOUND(26,"用户查找失败"),
    LOGOUT_SUCCESS(27,"用户退出成功"),
    WECHAT_MP_ERROR(28,"微信授权错误");

    private Integer code;
    private String message;

    RestEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
