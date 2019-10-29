package com.example.sell.utils;

import com.example.sell.enums.GetCode;

public class GetEnumUtils {

    public static <T extends GetCode>T getEnumByCode(Integer code, Class<T> enumClass){
        /*getEnumConstants() 获取当前枚举类的每一个枚举值 如果当前对象不表示枚举类则返回null*/
        for (T each : enumClass.getEnumConstants()) {
            if (code.equals(each.getCode())){
                return each;
            }
        }
        return null;
    }
}
