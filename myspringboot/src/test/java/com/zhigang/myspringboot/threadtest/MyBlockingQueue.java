/**
 * FileName: MyBlockingQueue
 * Author:   Administrator
 * Date:     2019/6/4 9:52
 * Description: 生产消费者模式应用阻塞队列
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.threadtest;

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
            wait();
        }
        queue.add(e);
        notifyAll();
    }

    public synchronized E take() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        E e = queue.poll();
        notifyAll();
        return e;
    }


}
