package com.zhigang.myspringboot.controller;

import com.zhigang.myspringboot.service.QueueMessageService;
import com.zhigang.myspringboot.utils.common.ExchangeEnum;
import com.zhigang.myspringboot.utils.common.QueueEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: Code
 * @Description MQ发送消息
 * @Author admin
 * @Date 2019/8/3 23:32
 * @Version 3.2.2
 **/
@RestController
@Slf4j
public class RabbitMqSendMsgCtr {

    @Resource
    private QueueMessageService queueMessageService;


    @GetMapping("/sendtomq")
    public String sendMsgToMq(@RequestParam("msg") String msg) {
        String result = "success";
        System.out.println(msg);
        try {
            this.queueMessageService.send(msg, ExchangeEnum.DIRCEXCHANGE, QueueEnum.MY_QUEUE);
        } catch (Exception e) {
            RabbitMqSendMsgCtr.log.error("", e);
            result = "failed";
        }
        return result;
    }
}
