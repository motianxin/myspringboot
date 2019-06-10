/**
 * FileName: ConsumerThread
 * Author:   Administrator
 * Date:     2019/6/4 10:07
 * Description: ConsumerThread
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.threadtest;

/**
 * 〈ConsumerThread〉
 *
 * @author Administrator
 * @version 3.2.2
 * @create 2019/6/4 10:07
 */
public class ConsumerThread extends Thread {
    private MyBlockingQueue<String> queue;

    public ConsumerThread(MyBlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            int num = 0;
            while (num < 10) {
                System.out.println("before ConsumerThread take task, state: " + this.getState());
                String task = queue.take();
                System.out.println("after ConsumerThread take task, state: " + this.getState());
                System.out.println("ConsumerThread take task: " + task);
                num++;
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
