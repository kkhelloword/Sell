package com.example.sell.VO;

import lombok.Data;

import java.io.Serializable;

/**
    * @Description:  返回对象封装类
        * @Author: baiyj
        * @Date: 2019/10/12
        */

@Data
public class ResultVO<T> implements Serializable {


    private static final long serialVersionUID = -1080063998721312799L;

    private Integer code;

    private String message;

    private T data;
}
