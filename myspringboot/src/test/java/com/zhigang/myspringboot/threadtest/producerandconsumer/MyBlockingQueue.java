/**
 * FileName: MyBlockingQueue
 * Author:   Administrator
 * Date:     2019/6/4 9:52
 * Description: 生产消费者模式应用阻塞队列
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.threadtest.producerandconsumer;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 〈生产消费者模式应用阻塞队列〉
 *
 * @author Administrator
 * @version 3.2.2
 * @create 2019/6/4 9:52
 */
public class MyBlockingQueue<E> {
    private Queue<E> queue = null;
    private int limit;

    public MyBlockingQueue(int limit) {
        this.queue = new ArrayDeque<>(limit);
        this.limit = limit;
    }

    public synchronized void put(E e) throws InterruptedException {
        while (queue.size() == limit) {
            // 队列满了，等待
            wait();
        }
        queue.add(e);
        notifyAll();
    }

    public synchronized E take() throws InterruptedException {
        while (queue.isEmpty()) {
            // 队列空了等待
            wait();
        }
        E e = queue.poll();
        //唤醒所有在条件等待队列中的线程
        notifyAll();
        return e;
    }
}
