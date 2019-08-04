package com.zhigang.myspringboot.domain;

import lombok.Data;

import java.util.Date;

/**
 * @program: Code
 * @Description 一句话描述
 * @Author admin
 * @Date 2019/8/4 11:20
 * @Version 3.2.2
 **/
@Data
public class KafkaMsg {
    private long id;
    private String hostIp;
    private String msg;
    private Date createTime;
}
