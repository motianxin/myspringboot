package com.zhigang.myspringboot.utils.common;

import lombok.Getter;

/**
 * @Description kafka topic name
 * @Author admin
 * @Date 11:13 2019/8/4
 * @Version 1.0
 **/
@Getter
public enum KafkaTopicEnum {
    MYTOPIC("mytopic");

    private String name;

    KafkaTopicEnum(String name) {
        this.name = name;
    }
}
