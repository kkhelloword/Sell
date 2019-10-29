package com.example.sell.service.impl;

import com.example.sell.dataobject.OrderMaster;
import com.example.sell.service.PushMessage;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class PushMessageImpl implements PushMessage {

    /*微信sdk*/
    @Autowired
    private WxMpService wxMpService;

    @Override
    public void orderStatus(OrderMaster orderMaster) {
        WxMpTemplateMessage wxMpTemplateMessage = new WxMpTemplateMessage();
        /*模板id*/
        wxMpTemplateMessage.setTemplateId("EZ_epIRPsPkVDeWxHGLsWObMiuSFFJLriW9ZA8volig");
        /*接收者的openid*/
        wxMpTemplateMessage.setToUser("omv52wRfaC9Adl6fqGqP3tSnvwVA");
        List<WxMpTemplateData> data = Arrays.asList(
                new WxMpTemplateData("first", "亲，请记得收货!")
        );
        /*模板数据*/
        wxMpTemplateMessage.setData(data);


        /*发送消息*/
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage);
        } catch (WxErrorException e) {
            log.error("【微信模板消息】模板消息发送失败,{}", e);
        }

    }
}
