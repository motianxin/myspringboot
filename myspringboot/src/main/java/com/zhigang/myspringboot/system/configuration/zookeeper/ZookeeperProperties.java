package com.zhigang.myspringboot.system.configuration.zookeeper;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @program: Code
 * @Description 一句话描述
 * @Author admin
 * @Date 2019/8/12 21:38
 * @Version 3.2.2
 **/
@Data
@Component
public class ZookeeperProperties {

    @Value("${zookeeper.baseSleepTimeMs}")
    private int baseSleepTimeMs;
    @Value("${zookeeper.maxRetries}")
    private int maxRetries;
    @Value("${zookeeper.server}")
    private String server;
    @Value("${zookeeper.sessionTimeoutMs}")
    private int sessionTimeoutMs;
    @Value("${zookeeper.connectionTimeoutMs}")
    private int connectionTimeoutMs;
    @Value("${zookeeper.namespace}")
    private String namespace;
    @Value("${zookeeper.digest}")
    private String digest;


}
