/**
 * FileName: WaitThread
 * Author:   Administrator
 * Date:     2019/6/4 9:01
 * Description: thread wait methoed test
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.threadtest;

/**
 * 〈thread wait methods test〉
 *
 * @author Administrator
 * @version 3.2.2
 * @create 2019/6/4 9:01
 */
public class WaitThread extends Thread {

    private volatile boolean fire = false;


    @Override
    public void run() {
        System.out.println("before wait: " + this.getState());
        try {
            synchronized (this) {
                while (!fire) {
                    wait();
                    System.out.println("after wait: " + this.getState());
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("thread run method, fire: " + fire);
    }


    public synchronized void fire() {
        this.fire = true;
        System.out.println("befor notify: " + this.getState());
        notify();
        System.out.println("after notify: " + this.getState());
    }


    /**
     * WaitThread state see
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        WaitThread waitThread = new WaitThread();
        System.out.println("befor start: " + waitThread.getState());
        waitThread.start();
        System.out.println("after start: " + waitThread.getState());
        System.out.println("start sleep 1 sec");
        Thread.sleep(1000);
        System.out.println("after sleep: " + waitThread.getState());
        System.out.println("main method, fire:" + waitThread.fire);
        waitThread.fire();
        System.out.println(waitThread.getState());
        // result
        /**
         * befor start: NEW
         * after start: RUNNABLE
         * start sleep 1 sec
         * before wait: RUNNABLE
         * main method, fire:false
         * befor notify: WAITING
         * after notify: BLOCKED
         * BLOCKED
         * after wait: RUNNABLE
         * thread run method, fire: true
         */
    }

}
