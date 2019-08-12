package com.zhigang.myspringboot.system.configuration.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/**
 * @program: Code
 * @Description 一句话描述
 * @Author admin
 * @Date 2019/8/12 21:56
 * @Version 3.2.2
 **/
public class ZookeeperConf {
    @Value("${zookeeper.server}")
    private String zkUrl;

    @Bean
    public CuratorFramework getCuratorFramework(){
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000,3);
        CuratorFramework client = CuratorFrameworkFactory.newClient(zkUrl,retryPolicy);
        client.start();
        return client;
    }
}
