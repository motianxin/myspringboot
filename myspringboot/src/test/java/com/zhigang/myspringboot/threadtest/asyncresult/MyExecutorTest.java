package com.zhigang.myspringboot.threadtest.asyncresult;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: Code
 * @Description 一句话描述
 * @Author admin
 * @Date 2019/7/9 22:54
 * @Version 3.2.2
 **/
public class MyExecutorTest {
    public static void main(String[] args) {
        MyExecutor executor = new MyExecutor();
        MyFuture<String> future = executor.execute(() -> new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));

        try {
            String now = future.get();
            System.out.println("this time is " + now);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
