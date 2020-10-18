package com.zhigang.myspringboot.domain;

import java.util.Date;

/**
 * @program: Code
 * @Description 一句话描述
 * @Author admin
 * @Date 2019/8/4 11:20
 **/
public record KafkaMsg(long id, String hostIp, String msg, Date createTime) {
}
