package com.zhigang.myspringboot.threadtest.leetcodethread;

/**
 * @program: Code
 * @Description 一句话描述
 * @Author admin
 * @Date 2019/8/21 12:19
 * @Version 3.2.2
 **/
public class ZeroEvenOddRun implements Runnable {

    private ZeroEvenOdd zeroEvenOdd;

    private int number;

    public ZeroEvenOddRun(ZeroEvenOdd zeroEvenOdd, int number) {
        this.zeroEvenOdd = zeroEvenOdd;
        this.number = number;
    }

    @Override
    public void run() {
        if (number == 0) {
            try {
                zeroEvenOdd.zero((i) -> System.out.print("0"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else if (number == 1) {
            try {
                zeroEvenOdd.even((i) -> System.out.print(i + ""));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            try {
                zeroEvenOdd.odd((i) -> System.out.print(i + ""));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
