package com.zhigang.myspringboot.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * @program: Code
 * @Description 一句话描述
 * @Author admin
 * @Date 2019/8/4 11:20
 **/
@Data
@AllArgsConstructor
public class KafkaMsg {
    private long id;

    private String hostIp;

    private String msg;

    private Date createTime;
}
