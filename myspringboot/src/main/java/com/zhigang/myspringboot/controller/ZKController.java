package com.zhigang.myspringboot.controller;

import com.zhigang.myspringboot.system.configuration.zookeeper.ZKlock;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: Code
 * @Description 一句话描述
 * @Author admin
 * @Date 2019/8/12 21:58
 * @Version 3.2.2
 **/
@RestController

public class ZKController {

    @Resource
    private ZKlock zklock;

    @GetMapping("/lock")
    public Boolean getLock() throws Exception {

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                this.zklock.lock();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.zklock.unlock();
            }, "thread-" + i).start();
        }
        return true;
    }
}

