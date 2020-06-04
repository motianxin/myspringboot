package com.zhigang.myspringboot.service;

import com.zhigang.myspringboot.utils.common.KafkaTopicEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @program: Code
 * @Description 一句话描述
 * @Author admin
 * @Date 2019/8/4 11:07
 * @Version 3.2.2
 **/
@Service
@Slf4j
public class KafkaServiceImpl implements KafkaService {

    @Resource
    private KafkaTemplate kafkaTemplate;

    @Override
    @Async("logThread")
    public void sendMessage(String message) throws Exception {
        kafkaTemplate.send(KafkaTopicEnum.MYTOPIC.getName(), message);
    }
}
