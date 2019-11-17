package com.zhigang.myspringboot.threadtest.leetcodethread;

/**
 * @program: Code
 * @Description 一句话描述
 * @Author admin
 * @Date 2019/8/21 11:26
 * @Version 3.2.2
 **/
public class FooBar {
    Object lock = new Object();
    private int n;
    private boolean foo = true;
    private boolean bar = false;

    public FooBar(int n) {
        this.n = n;
    }

    public static void main(String[] args) {
        FooBar fooBar = new FooBar(6);
        new Thread(new FooBarThread(fooBar, 0)).start();
        new Thread(new FooBarThread(fooBar, 1)).start();
    }

    public void foo(Runnable printFoo) throws InterruptedException {

        for (int i = 0; i < this.n; i++) {
            synchronized (this.lock) {
                while (!this.foo) {
                    this.lock.wait();
                }
                // printFoo.run() outputs "foo". Do not change or remove this line.
                printFoo.run();
                this.foo = false;
                this.bar = true;
                this.lock.notifyAll();
            }
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {

        for (int i = 0; i < this.n; i++) {
            synchronized (this.lock) {
                while (!this.bar) {
                    this.lock.wait();
                }
                // printBar.run() outputs "bar". Do not change or remove this line.
                printBar.run();
                this.foo = true;
                this.bar = false;
                this.lock.notifyAll();
            }
        }
    }

}
