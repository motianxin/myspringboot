package com.zhigang.myspringboot.threadtest.leetcodethread;

/**
 * @program: Code
 * @Description 一句话描述
 * @Author admin
 * @Date 2019/8/21 11:40
 * @Version 3.2.2
 **/
public class FooBarThread implements Runnable {

    private FooBar fooBar;

    private int i;

    public FooBarThread(FooBar fooBar, int i) {
        this.fooBar = fooBar;
        this.i = i;
    }

    @Override
    public void run() {
        if (i == 0) {
            try {
                fooBar.foo(() -> System.out.print("foo"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            try {
                fooBar.bar(() -> System.out.print("bar"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
