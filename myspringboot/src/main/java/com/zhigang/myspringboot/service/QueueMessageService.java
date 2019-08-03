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
     * @Description: 生产者发送消息方法
     * @Param: [message, exchangeEnum, queueEnum] 
     * @return: void 
     * @Author: admin
     * @Date: 2019/8/3 23:58
     */
    void send(Object message, ExchangeEnum exchangeEnum, QueueEnum queueEnum) throws Exception;
}
