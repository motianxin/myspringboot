/**
 * FileName: ProducerThread
 * Author:   Administrator
 * Date:     2019/6/4 10:00
 * Description: ProducerThread
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.threadtest;

/**
 * 〈ProducerThread〉
 *
 * @author Administrator
 * @version 3.2.2
 * @create 2019/6/4 10:00
 */
public class ProducerThread extends Thread {

    private MyBlockingQueue<String> queue;

    public ProducerThread(MyBlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        int num = 0;
        try {
            while (num < 10) {
                String taskNum = String.valueOf(num);
                System.out.println("before ProducerThread put task, state: " + this.getState());
                queue.put(taskNum);
                System.out.println("after ProducerThread put task, state: " + this.getState());
                System.out.println("ProducerThread put task: " + taskNum);
                num++;
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
