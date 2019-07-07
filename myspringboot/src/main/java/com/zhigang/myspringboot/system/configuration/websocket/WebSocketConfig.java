package com.zhigang.myspringboot.system.configuration.websocket;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @program: Code
 * @Description 一句话描述
 * @Author admin
 * @Date 2019/6/29 23:20
 * @Version 3.2.2
 **/
@Configurable
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }
}

