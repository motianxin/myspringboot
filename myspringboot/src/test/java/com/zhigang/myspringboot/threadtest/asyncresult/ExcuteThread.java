package com.zhigang.myspringboot.threadtest.asyncresult;

import java.util.concurrent.Callable;

/**
 * @program: Code
 * @Description 一句话描述
 * @Author admin
 * @Date 2019/7/9 22:43
 * @Version 3.2.2
 **/
public class ExcuteThread<V> extends Thread {

    private V result;

    private Exception exception;

    private Callable<V> task;

    private Object lock;

    private boolean done = false;

    public ExcuteThread(Callable<V> task, Object lock) {
        this.task = task;
        this.lock = lock;
    }

    public V getResult() {
        return result;
    }

    public Exception getException() {
        return exception;
    }

    public Callable<V> getTask() {
        return task;
    }

    public Object getLock() {
        return lock;
    }

    public boolean isDone() {
        return done;
    }

    @Override
    public void run() {
        try {
            result = task.call();
        } catch (Exception e) {
            exception = e;
        } finally {
            synchronized (lock){
                done = true;
                lock.notifyAll();
            }
        }
    }
}
