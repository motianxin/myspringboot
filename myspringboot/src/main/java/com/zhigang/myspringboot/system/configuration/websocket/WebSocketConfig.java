package com.zhigang.myspringboot.system.configuration.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @program: Code
 * @Description 一句话描述
 * @Author admin
 * @Date 2019/6/29 23:20
 * @Version 3.2.2
 **/
@Configuration
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }
}

