package com.zhigang.myspringboot.threadtest.waitsameover;

import java.util.Random;

/**
 * @program: Code
 * @Description 主线程等待子线程同时结束协作对象
 * @Author admin
 * @Date 2019/7/9 22:08
 * @Version 3.2.2
 **/
public class MyLatch {

    private int count;

    public MyLatch(int count) {
        this.count = count;
    }

    public synchronized void await() throws InterruptedException {
        while (this.count > 0) {
            System.out.println("latch state is " + Thread.currentThread().getState());
            wait();
        }
    }

    public synchronized void countDown() throws InterruptedException {
        this.count--;
        if (this.count == 0) {
            notifyAll();
            Thread.sleep(new Random().nextInt(1000));
        }
    }
}
