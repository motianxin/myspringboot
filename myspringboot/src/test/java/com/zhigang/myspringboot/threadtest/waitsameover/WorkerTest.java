package com.zhigang.myspringboot.threadtest.waitsameover;

/**
 * @program: Code
 * @Description 一句话描述
 * @Author admin
 * @Date 2019/7/9 22:17
 * @Version 3.2.2
 **/
public class WorkerTest {
    public static void main(String[] args) {
        int a = 5;
        MyLatch latch = new MyLatch(a);
        Worker[] workers = new Worker[a];
        for (int i = 0; i < a; i++) {
            workers[i] = new Worker(latch);
            workers[i].start();
        }
        for (int i = 0; i < a; i++) {
            System.out.println("worker" + i + "'s state is " + workers[i].getState());
        }
        try {
            latch.await();
            System.out.println("main's state is " + Thread.currentThread().getState());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
