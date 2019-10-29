package com.example.sell.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Component
public class webSocketConfig {

    @Bean
    public ServerEndpointExporter getServerEndpointExporter(){
            return new ServerEndpointExporter();
    }
}
