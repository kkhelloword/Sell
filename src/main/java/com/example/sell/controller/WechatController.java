package com.example.sell.controller;

import com.example.sell.enums.RestEnum;
import com.example.sell.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;

/**
 * 微信授权获取openid  使用第三方sdk
 * @Author: baiyj
 * @Date: 2019/10/16
 */

@Controller  // restController 重定向不会跳转所以我们用controller
@RequestMapping("/wechat")
@Slf4j
public class WechatController {

    @Autowired
    private WxMpService wxMpService;

    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl){
        // 1. 配置
        // 2. 调用方法
        String url = "http://tc4g7q.natappfree.cc/sell/wechat/userInfo";
        // 重定向url  获取范围 USER_INFO 获取的更多   state传过去什么回来什么，传的是回调的url
        // 返回值是重定向的url
        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAUTH2_SCOPE_USER_INFO, URLEncoder.encode(returnUrl));
        return "redirect:" + redirectUrl;
    }

    /**
    * 获取openid
    *@param
    *@return
    */
    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code") String code,
                           @RequestParam("state")String returnUrl){
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("【微信网页授权】微信授权错误,{}",e);
            throw new SellException(RestEnum.WECHAT_MP_ERROR);
        }
        String openid = wxMpOAuth2AccessToken.getOpenId();
        System.out.println(openid);
        return "redirect:" + returnUrl + "?openid="+openid;
    }
}
