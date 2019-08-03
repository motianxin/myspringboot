package com.zhigang.myspringboot.system.configuration.rabbitmq;

import com.zhigang.myspringboot.utils.common.QueueEnum;
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
@RabbitListener(queues = QueueEnum.NAME)
public class RabbitMqConsumer {

    @RabbitHandler
    public void takeMsg(String msg){
      log.info("开始消费消息， id 为 {}", msg);
    }


}
