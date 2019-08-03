package com.zhigang.myspringboot.utils.common;

import lombok.Getter;

/**
 * @Description rabbitMQ交换机枚举类
 * @Author admin
 * @Date 23:26 2019/8/3
 * @Version 1.0
 **/
@Getter
public enum ExchangeEnum {

    MY_EXCHANGE("zhigangExchange"),
    TEST_EXCHANGE("test")

    ;



    private String exchangeName;

    ExchangeEnum(String exchangeName) {
        this.exchangeName = exchangeName;
    }
}
