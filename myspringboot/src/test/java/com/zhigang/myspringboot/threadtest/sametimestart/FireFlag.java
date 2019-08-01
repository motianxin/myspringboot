package com.zhigang.myspringboot.threadtest.sametimestart;

/**
 * @program: Code
 * @Description FireFlag
 * @Author admin
 * @Date 2019/7/9 21:33
 * @Version 3.2.2
 **/
public class FireFlag {

    private volatile boolean flag = false;
    public synchronized void  waitFlag() throws InterruptedException {
        while (!flag) {
            wait();
        }
        System.out.println("FireFlag : "+Thread.currentThread().getState());
    }
    public synchronized void setFlag (){
        this.flag = true;
        notifyAll();
        System.out.println("after notifyall FireFlag : "+Thread.currentThread().getState());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
