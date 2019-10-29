package com.example.sell.controller;


import com.example.sell.dataobject.SellerInfo;
import com.example.sell.enums.RestEnum;
import com.example.sell.exception.SellException;
import com.example.sell.service.SellerService;
import com.example.sell.utils.CookieUtils;
import constant.CookieConstant;
import constant.RedisConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 用户登录
 * @Author: baiyj
 * @Date: 2019/10/23
 */

@Controller
@RequestMapping("/seller")
@Slf4j
public class SellerUserController {

    @Autowired
    private SellerService sellerService;

    @Value("${redirectUrl}")
    private String redirectUrl;

    @Autowired
    private StringRedisTemplate redisTemplate;
    /**
    * 登录
    *@param  [openid]
    *@return void
    */
    @GetMapping("/login")
    public String login(@RequestParam("openid")String openid,
                      HttpServletResponse response){
        //1. openid 去和数据库匹配
        SellerInfo byOpenid = sellerService.findByOpenid(openid);
        if (byOpenid == null){
            log.error("【用户登录】 用户查找失败,SellerInfo={}",byOpenid);
            throw new SellException(RestEnum.OPENID_NOTFOUND);
        }
        //2. 将token写入redis
        String token = UUID.randomUUID().toString();
        Integer expire = RedisConstant.EXPIRE;
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN,token),openid,expire,TimeUnit.SECONDS);

        //3. 将token写入cookie
        CookieUtils.set(response, CookieConstant.token,token,expire);
        //4.跳转到首页
        return "redirect:" + redirectUrl;
    }

    /**
    *退出
    *@param  [openid]
    *@return void
    */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response,
                         Model model){


        // 1. 清除cookie数据
        Cookie cookie = CookieUtils.get(request, CookieConstant.token);
        if (cookie!=null){

            // 2. 清除redis数据
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN,cookie.getValue()));
            CookieUtils.set(response,CookieConstant.token,null,0);
        }
        // 3. 跳转
        model.addAttribute("msg",RestEnum.LOGOUT_SUCCESS);
        model.addAttribute("url","/sell/seller/order/list");
        return "/common/success";

    }
}
