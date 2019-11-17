package com.zhigang.myspringboot.system.configuration.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


/**
 * @program: Code
 * @Description MQ消费者
 * @Author admin
 * @Date 2019/8/4 0:02
 * @Version 3.2.2
 **/
@Component
@Slf4j
@RabbitListener(queues = "direc_queue")
public class RabbitMqConsumer {
    @RabbitHandler
    public void onMessage(Object msg) {
        RabbitMqConsumer.log.info("开始消费消息， id 为 {}", msg);
    }


}
