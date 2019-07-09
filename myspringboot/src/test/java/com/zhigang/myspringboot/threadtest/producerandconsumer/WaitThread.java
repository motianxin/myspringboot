/**
 * FileName: WaitThread
 * Author:   Administrator
 * Date:     2019/6/4 9:01
 * Description: thread wait methoed test
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.threadtest.producerandconsumer;

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
                // 条件不满足进行等待
                while (!fire) {
                    // wait方法将线程放入条件等待队列，线程让出锁并阻塞，WAITING
                    // 被唤醒后尝试获取锁，获取不到进入锁等待队列继续阻塞，blocked,wait还在继续
                    wait();
                    // wait跳出后说明已经获得了锁，但需要再次检查条件是否满足
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
        // 修改条件并唤醒条件等待队列中的一个线程
        notify();
        // 方法执行完后wait方法跳出，但是该方法还没有结束，
        // 继续执行，仍然得到锁，条件等待队列中的线程被唤醒后进入锁等待队列，状态为blocked，等待锁
        System.out.println("after notify: " + this.getState());
        //方法执行完后，锁等待队列中的线程竞争锁继续执行
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
