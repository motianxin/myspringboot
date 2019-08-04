package com.zhigang.myspringboot.controller;

import com.zhigang.myspringboot.service.QueueMessageService;
import com.zhigang.myspringboot.utils.common.ExchangeEnum;
import com.zhigang.myspringboot.utils.common.QueueEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @Autowired
    private QueueMessageService queueMessageService;


    @GetMapping("/sendtomq")
    public String sendMsgToMq(@RequestParam("msg") String msg){
        String result = "success";
        try {
            queueMessageService.send(msg, ExchangeEnum.DIRCEXCHANGE, QueueEnum.MY_QUEUE);
        } catch (Exception e) {
            log.error("", e);
            result = "failed";
        }
        return result;
    }


}
