package com.zhigang.myspringboot.threadtest.sametimestart;

/**
 * @program: Code
 * @Description 一句话描述
 * @Author admin
 * @Date 2019/7/9 21:38
 * @Version 3.2.2
 **/
public class Racer extends Thread {
    private FireFlag fireFlag = null;

    private String name;

    public Racer(FireFlag fireFlag, String name) {
        this.name = name;
        this.fireFlag = fireFlag;
    }

    public String getThreadName() {
        return this.name;
    }

    @Override
    public void run() {
        try {
            System.out.println("start wait flag");
            this.fireFlag.waitFlag();
            System.out.println("end wait flag");
            System.out.println(this.name + "'s state is " + Thread.currentThread().getState());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
