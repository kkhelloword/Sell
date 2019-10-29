package com.example.sell.config;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
/**
 * @Author: baiyj
 * @Date: 2019/10/16
 */

@Component
public class WechatMpConfig {

    @Autowired
    private Environment env;

    @Bean
    public WxMpService wxMpService(){
        WxMpServiceImpl wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(getWxMpConfigStorage());
        return wxMpService;
    }

    public WxMpConfigStorage getWxMpConfigStorage(){
        WxMpInMemoryConfigStorage wxMpConfigStorage = new WxMpInMemoryConfigStorage();
        wxMpConfigStorage.setAppId(env.getProperty("wechat.mpAppId"));
        wxMpConfigStorage.setSecret(env.getProperty("wechat.myAppSecret"));
        return wxMpConfigStorage;
    }
}
