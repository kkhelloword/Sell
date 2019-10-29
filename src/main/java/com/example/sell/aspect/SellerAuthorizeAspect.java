package com.example.sell.aspect;

import com.example.sell.exception.SellAuthorException;
import com.example.sell.utils.CookieUtils;
import constant.CookieConstant;
import constant.RedisConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 切面拦截登录
 * @Author: baiyj
 * @Date: 2019/10/23
 */

@Aspect
@Component
@Slf4j
public class SellerAuthorizeAspect {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Pointcut("execution(public * com.example.sell.controller.Seller*.*(..))" +
            "&& !execution(public * com.example.sell.controller.SellerUserController*.*(..))")
    public void verify(){};

    @Before("verify()")
    public void doVerify(){
        /*获得request*/
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        //查询cookie
        Cookie cookie = CookieUtils.get(request, CookieConstant.token);
        if (cookie == null){
            log.warn("【卖家切面】 cookie查询token失败");
            throw new SellAuthorException();
        }

        // 查询redis
        String redisValue = redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN, cookie.getValue()));
        if (StringUtils.isEmpty(redisValue)){
            log.warn("【卖家切面】 redis查询token失败");
            throw new SellAuthorException();
        }

    }
}
