package com.example.sell.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@Slf4j
@Service
@ServerEndpoint("/websocket")
public class WebSocket {

    private Session session;

    private static CopyOnWriteArraySet<WebSocket> websocketSet = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        websocketSet.add(this);
        log.info("【websocket 消息发送】 有新连接的总数，总数:{}",websocketSet.size());
    }

    @OnClose
    public void OnClose(){
        websocketSet.remove(this);
        log.info("【websocket 消息发送】 连接断开，总数:{}",websocketSet.size());
    }

    @OnMessage
    public void OnMessage(String message){
        log.info("【websocket 消息发送】 收到客户端的消息,消息={}",message);
    }

    /*广播消息*/
    public void sendMessage(String message){
        for (WebSocket webSocket : websocketSet) {
            log.info("【websocket 消息】广播消息,message={}",message);
            try {
                webSocket.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                log.info("【websocket 消息】广播消息,message={}",message);
                e.printStackTrace();
            }
        }
    }
}
