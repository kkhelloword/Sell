package com.example.sell.utils;

import java.util.Random;

/**
 * @Author: baiyj
 * @Date: 2019/10/15
 */

public class KeyUtils {
      /**
       *  生成唯一的主键
       *@return
       */
      public static synchronized String getUniqueKey(){
          Random random = new Random();
          int i = random.nextInt(90000) + 100000;
          return System.currentTimeMillis() + String.valueOf(i);
      }
}
