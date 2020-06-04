package com.zhigang.myspringboot.controller;

import com.zhigang.myspringboot.domain.KafkaMsg;
import com.zhigang.myspringboot.service.KafkaService;
import com.zhigang.myspringboot.utils.strutils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @program: Code
 * @Description 一句话描述
 * @Author admin
 * @Date 2019/8/4 10:35
 * @Version 3.2.2
 **/
@Slf4j
@RestController
public class KafkaSendController {

    @Resource
    private KafkaService kafkaService;

    @GetMapping("/sendtoka")
    public String sendMsgToKafka(@RequestParam("msg") String msg) {
        log.info("begin send msg {} to kafka.", msg);
        String result = "success";
        KafkaMsg kafkaMsg = new KafkaMsg();
        Date createTime = new Date();
        kafkaMsg.setCreateTime(createTime);
        kafkaMsg.setId(createTime.getTime());
        kafkaMsg.setMsg(msg);
        kafkaMsg.setHostIp("127.0.0.1");
        try {
            kafkaService.sendMessage(JsonUtils.obj2Json(kafkaMsg));
        } catch (Exception e) {
            log.error("send kafka msg failed: msg is {}.", msg, e);
            result = "failed";
        }
        return result;
    }
}
