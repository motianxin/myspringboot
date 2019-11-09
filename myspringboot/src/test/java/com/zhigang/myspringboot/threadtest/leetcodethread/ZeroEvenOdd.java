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

    private boolean zero = true;

    private boolean even = false;

    private boolean odd = false;

    Object lock = new Object();

    private static volatile int i = 0;
    private int n;

    public ZeroEvenOdd(int n) {
        this.n = n;
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
        while (i/2 < n-1) {
            synchronized (lock) {
                while (!zero) {
                    lock.wait();
                }
                zero = false;
                printNumber.accept(0);
                i++;
                if ((((i - 1)/ 2) & 1) == 1) {
                    odd = true;
                } else {
                    even = true;
                }
                if (i/2 == n -1) {
                    even = true;
                    odd = true;
                }
                lock.notifyAll();
            }
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        while (i/2 + 1 <= n) {
            synchronized (lock) {
                while (!even) {
                    lock.wait();
                }
                zero = true;
                even = false;
                if (i/2 + 1 <= n) {
                    printNumber.accept(i / 2 + 1);
                    i++;
                }
                lock.notifyAll();
            }
        }

    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        while (i/2 + 1 <= n) {
            synchronized (lock) {
                while (!odd) {
                    lock.wait();
                }
                zero = true;
                odd = false;
                if (i/2 + 1 <= n) {
                    printNumber.accept(i / 2 + 1);
                    i++;
                }
                lock.notifyAll();
            }
        }
    }

    public static void main(String[] args) {
        ZeroEvenOdd zeroEvenOdd = new ZeroEvenOdd(2);
        new Thread(new ZeroEvenOddRun(zeroEvenOdd, 0)).start();
        new Thread(new ZeroEvenOddRun(zeroEvenOdd, 1)).start();
        new Thread(new ZeroEvenOddRun(zeroEvenOdd, 2)).start();
    }
}
