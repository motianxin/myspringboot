package com.zhigang.myspringboot.threadtest.waitsameover;

import java.util.Random;

/**
 * @program: Code
 * @Description 一句话描述
 * @Author admin
 * @Date 2019/7/9 22:14
 * @Version 3.2.2
 **/
public class Worker extends Thread {
    private MyLatch latch;

    public Worker(MyLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(new Random().nextInt(1000));
            latch.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
