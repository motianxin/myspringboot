package com.zhigang.myspringboot.threadtest.leetcodethread;

import java.util.function.IntConsumer;

/**
 * @program: Code
 * @Description 一句话描述
 * @Author admin
 * @Date 2019/8/21 11:44
 * @Version 3.2.2
 **/
public class ZeroEvenOdd {

    private static volatile int i = 0;
    Object lock = new Object();
    private boolean zero = true;
    private boolean even = false;
    private boolean odd = false;
    private int n;

    public ZeroEvenOdd(int n) {
        this.n = n;
    }

    public static void main(String[] args) {
        ZeroEvenOdd zeroEvenOdd = new ZeroEvenOdd(2);
        new Thread(new ZeroEvenOddRun(zeroEvenOdd, 0)).start();
        new Thread(new ZeroEvenOddRun(zeroEvenOdd, 1)).start();
        new Thread(new ZeroEvenOddRun(zeroEvenOdd, 2)).start();
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
        while (ZeroEvenOdd.i / 2 < this.n - 1) {
            synchronized (this.lock) {
                while (!this.zero) {
                    this.lock.wait();
                }
                this.zero = false;
                printNumber.accept(0);
                ZeroEvenOdd.i++;
                if ((((ZeroEvenOdd.i - 1) / 2) & 1) == 1) {
                    this.odd = true;
                } else {
                    this.even = true;
                }
                if (ZeroEvenOdd.i / 2 == this.n - 1) {
                    this.even = true;
                    this.odd = true;
                }
                this.lock.notifyAll();
            }
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        while (ZeroEvenOdd.i / 2 + 1 <= this.n) {
            synchronized (this.lock) {
                while (!this.even) {
                    this.lock.wait();
                }
                this.zero = true;
                this.even = false;
                if (ZeroEvenOdd.i / 2 + 1 <= this.n) {
                    printNumber.accept(ZeroEvenOdd.i / 2 + 1);
                    ZeroEvenOdd.i++;
                }
                this.lock.notifyAll();
            }
        }

    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        while (ZeroEvenOdd.i / 2 + 1 <= this.n) {
            synchronized (this.lock) {
                while (!this.odd) {
                    this.lock.wait();
                }
                this.zero = true;
                this.odd = false;
                if (ZeroEvenOdd.i / 2 + 1 <= this.n) {
                    printNumber.accept(ZeroEvenOdd.i / 2 + 1);
                    ZeroEvenOdd.i++;
                }
                this.lock.notifyAll();
            }
        }
    }
}
