package com.zhigang.myspringboot.system.configuration.kafka;

import com.zhigang.myspringboot.domain.KafkaMsg;
import com.zhigang.myspringboot.utils.strutils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @program: Code
 * @Description 一句话描述
 * @Author admin
 * @Date 2019/8/4 11:31
 * @Version 3.2.2
 **/
@Component
@Slf4j
public class KafkaConsumers {


    @KafkaListener(topics = {"mytopic"})
    public void consumerHandle(ConsumerRecord<?, ?> record) {
        KafkaConsumers.log.info("kafka consumer recever msg.");
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            String message = (String) kafkaMessage.get();
            KafkaConsumers.log.info("----------------- record =" + record);
            KafkaMsg kafkaMsg = JsonUtils.json2Obj(message, KafkaMsg.class);
            KafkaConsumers.log.info("Receive kafkaMsg  is {}", kafkaMsg);
        }
    }
}