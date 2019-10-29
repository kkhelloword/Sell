package com.example.sell.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * redis 分布式锁
 * @Author: baiyj
 * @Date: 2019/10/28
 */

@Component
@Slf4j
public class RedisLock {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
    *加锁  key是商品id  value是当前时间加超时时间
    *@param  [key, value]
    *@return boolean
    */
    public boolean lock(String key,String value){
        // 想当于redis命令  setnx key存在什么都不做 可以不存在set值
        if (redisTemplate.opsForValue().setIfAbsent(key, value)){
            return true;
        }
        String currentTime = redisTemplate.opsForValue().get(key);
        // 如果锁过期
        if (!StringUtils.isEmpty(currentTime) && Long.parseLong(currentTime) > System.currentTimeMillis()){
            // 获取上一个锁的值
            String oldValue = redisTemplate.opsForValue().getAndSet(key, value);
            if (!StringUtils.isEmpty(oldValue) && oldValue.equals(currentTime)){
                return true;
            }
        }
        return  false;
    }

    /**
    * 解锁
    *@param  [key, value]
    *@return void
    */
    public void unlock(String key,String value){
        try {
           String currentValue = redisTemplate.opsForValue().get(key);
           if (!StringUtils.isEmpty(currentValue) && value.equals(currentValue)){
               redisTemplate.opsForValue().getOperations().delete(key);
           }
        } catch (Exception e) {
            log.error("【redis分布式锁】 解锁异常,{}",e);
        }
    }
}
