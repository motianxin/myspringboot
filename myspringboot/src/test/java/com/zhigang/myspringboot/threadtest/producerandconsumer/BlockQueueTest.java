/**
 * FileName: BlockQueueTest
 * Author:   Administrator
 * Date:     2019/6/4 10:11
 * Description: BlockQueueTest
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.threadtest.producerandconsumer;

/**
 * 〈BlockQueueTest〉
 *
 * @author Administrator
 * @version 3.2.2
 * @create 2019/6/4 10:11
 */
public class BlockQueueTest {

    public static void main(String[] args) throws InterruptedException {
        MyBlockingQueue<String> queue = new MyBlockingQueue<>(5);
        ProducerThread producerThread = new ProducerThread(queue);
        ConsumerThread consumerThread = new ConsumerThread(queue);
        System.out.println("producerThread state: " + producerThread.getState());
        System.out.println("consumerThread state: " + consumerThread.getState());
        producerThread.start();
        consumerThread.start();
        int time = 0;
        while (time < 100) {
            time++;
            System.out.println("producerThread state: " + producerThread.getState());
            System.out.println("consumerThread state: " + consumerThread.getState());
            Thread.sleep(100);
        }
    }
}
