package com.zhigang.myspringboot.threadtest.asyncresult;

import java.util.concurrent.Callable;

/**
 * @program: Code
 * @Description 一句话描述
 * @Author admin
 * @Date 2019/7/9 22:38
 * @Version 3.2.2
 **/
public class MyExecutor {

    public <V> MyFuture<V> execute(final Callable<V> task) {
        Object lock = new Object();
        ExcuteThread<V> excuteThread = new ExcuteThread<>(task, lock);
        excuteThread.start();
        return () -> {
                synchronized (lock){
                    while (!excuteThread.isDone()){
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (excuteThread.getException() != null) {
                        throw excuteThread.getException();
                    }
                    return excuteThread.getResult();
                }
        };
    }
}
