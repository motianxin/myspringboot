package com.zhigang.myspringboot.utils.common;

import lombok.Getter;

/**
 * @Description rabbitMQ队列枚举类
 * @Author admin
 * @Date 23:27 2019/8/3
 * @Version 1.0
 **/
@Getter
public enum QueueEnum {
    MY_QUEUE("direc_queue", "queue"), TEST_QUEUE("test_queue", "test");

    public final static String NAME = "direc_queue";
    private String queueName;

    private String routingKey;

    QueueEnum(String queueName, String routingKey) {
        this.queueName = queueName;
        this.routingKey = routingKey;
    }
}
