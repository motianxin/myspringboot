/**
 * FileName: BlockQuenuByCondition
 * Author:   Administrator
 * Date:     2019/7/11 11:30
 * Description: 通过显示条件和显示锁实现阻塞队列
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.threadtest.producerandconsumer;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 〈通过显示条件和显示锁实现阻塞队列〉
 *
 * @author Administrator
 * @version 3.2.2
 * @create 2019/7/11 11:30
 */
public class BlockQuenuByCondition<E> {

    private Queue<E> queue = null;
    private int limit;


    ReentrantLock lock = new ReentrantLock();

    Condition notFull = lock.newCondition();

    Condition notEmpty = lock.newCondition();

    public BlockQuenuByCondition(int limit) {
        this.queue = new ArrayDeque<>(limit);
        this.limit = limit;
    }

    public void put(E e) throws InterruptedException {
        lock.lockInterruptibly();

        try {
            while (queue.size() == limit) {
                notFull.await();
            }
            queue.add(e);
            // 避免了不必要的唤醒和检查，提高了效率
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }


    public E take() throws InterruptedException {
        lock.lockInterruptibly();
        try {
            while (queue.isEmpty()) {
                notEmpty.await();
            }
            // 避免了不必要的唤醒和检查，提高了效率
            notFull.signal();
            return queue.poll();
        } finally {
            lock.unlock();
        }
    }

}
