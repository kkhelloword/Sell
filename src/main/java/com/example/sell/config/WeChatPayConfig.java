package com.example.sell.config;

import com.lly835.bestpay.config.WxPayH5Config;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class WeChatPayConfig {

    @Autowired
    private Environment environment;

    @Bean
    public BestPayServiceImpl bestPayService(){
        BestPayServiceImpl bestPayService = new BestPayServiceImpl();
        bestPayService.setWxPayH5Config(this.wxPayConfig());
        return bestPayService;
    }

    @Bean
    public WxPayH5Config wxPayConfig(){
        WxPayH5Config wxPayConfig = new WxPayH5Config();
        wxPayConfig.setAppId(environment.getProperty("wechat.mpAppId"));
        wxPayConfig.setAppSecret(environment.getProperty("wechat.myAppSecret"));
        wxPayConfig.setMchId(environment.getProperty("wechat.mchId"));
        wxPayConfig.setMchKey(environment.getProperty("wechat.mchkey"));
        wxPayConfig.setKeyPath(environment.getProperty("wechat.keyPath"));
        wxPayConfig.setNotifyUrl(environment.getProperty("wechat.notifyUrl"));
        return wxPayConfig;
    }
}
