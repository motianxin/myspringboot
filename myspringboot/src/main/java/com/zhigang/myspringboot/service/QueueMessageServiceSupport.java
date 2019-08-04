package com.zhigang.myspringboot.service;

import com.zhigang.myspringboot.utils.common.ExchangeEnum;
import com.zhigang.myspringboot.utils.common.QueueEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @program: Code
 * @Description 一句话描述
 * @Author admin
 * @Date 2019/8/3 23:30
 * @Version 3.2.2
 **/
@Service
@Slf4j
public class QueueMessageServiceSupport implements QueueMessageService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void send(String message, ExchangeEnum exchangeEnum, QueueEnum queueEnum) throws Exception {
        log.info("begin send msg to MQ!");
        //设置回调为当前类对象
        rabbitTemplate.setConfirmCallback(this);
        //构建回调id为uuid
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        //发送消息到消息队列
        rabbitTemplate.convertAndSend(exchangeEnum.getExchangeName(),
                queueEnum.getQueueName(), message, correlationId);
    }

    /**
     * @Description: 发送消息的回调方法
     * @Param: [correlationData, b, s]
     * @return: void
     * @Author: admin
     * @Date: 2019/8/3 23:31
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        log.info("开始回调： id 为 {}.", correlationData.getId());
        if (ack) {
            log.info("send msg success");
        } else {
            log.info("send msg failed, {}.", cause);
        }
    }
}
