package com.zhigang.myspringboot.service;

import com.zhigang.myspringboot.utils.common.ExchangeEnum;
import com.zhigang.myspringboot.utils.common.QueueEnum;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * @Description 生产者接口
 * @Author admin
 * @Date 23:28 2019/8/3
 * @Version 1.0
 **/
public interface QueueMessageService extends RabbitTemplate.ConfirmCallback {
    /**
     *
     *
     * @param message String
     * @param exchangeEnum ExchangeEnum
     * @param queueEnum QueueEnum
     * @throws Exception Exceptions
     */
    void send(String message, ExchangeEnum exchangeEnum, QueueEnum queueEnum) throws Exception;
}
