package com.zhigang.myspringboot.threadtest.sametimestart;

/**
 * @program: Code
 * @Description 一句话描述
 * @Author admin
 * @Date 2019/7/9 21:42
 * @Version 3.2.2
 **/
public class RacerTest {
    public static void main(String[] args) {
        FireFlag flag = new FireFlag();
        int sum = 5;
        Racer[] racers = new Racer[sum];
        for (int i = 0; i < sum; i++) {
            racers[i] = new Racer(flag, "racer" + i);
            racers[i].start();
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("before set flag");
        for (int i = 0; i < sum; i++) {
            System.out.println(racers[i].getThreadName() +  " 's state is " + racers[i].getState());
        }
        flag.setFlag();
        System.out.println("after set flag");
        for (int i = 0; i < sum; i++) {
            System.out.println(racers[i].getThreadName() + " 's state is " + racers[i].getState());
        }
    }
}
