package com.zhigang.myspringboot.service;

import org.springframework.stereotype.Service;

/**
 * @program: Code
 * @Description 一句话描述
 * @Author admin
 * @Date 2019/8/4 11:05
 * @Version 3.2.2
 **/
@Service
public interface KafkaService {
    void sendMessage(String message) throws Exception;
}
