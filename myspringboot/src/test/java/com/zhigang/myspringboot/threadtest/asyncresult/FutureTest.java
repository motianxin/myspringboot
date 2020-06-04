package com.zhigang.myspringboot.threadtest.asyncresult;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * <一句话功能描述>
 *
 * @Author 墨天心
 * @Since 2020/5/19 0:22
 */
public class FutureTest {

    public static void main(String[] args) {
        int[] data = new int[]{2, 5, 2, 6, 2, 1};
        FutureTask<Long> future = new FutureTask<>(new MyCall(10));
        ExecutorService executor = Executors.newFixedThreadPool(1);
        executor.submit(future);
        int i = 0;
        while (!future.isDone()) {
            try {
                Thread.sleep(3 * 1000);
                if (data[i++] == 1) {
                    future.cancel(true);
                    executor.shutdown();
                    return;
                }
                System.out.println(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            System.out.println(future.get());
            executor.shutdown();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }


    public static class MyCall implements Callable<Long> {
        private final int time;

        public MyCall(int time) {
            this.time = time;
        }

        @Override
        public Long call() throws Exception {
            try {
                Thread.sleep(time * 1000);
                return 100L;
            } catch (InterruptedException e) {
                e.printStackTrace();
                return 0L;
            }
        }
    }
}
