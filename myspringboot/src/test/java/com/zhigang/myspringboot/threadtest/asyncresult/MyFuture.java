package com.zhigang.myspringboot.threadtest.asyncresult;

/**
 * @program: Code
 * @Description 一句话描述
 * @Author admin
 * @Date 2019/7/9 22:35
 * @Version 3.2.2
 **/
public interface MyFuture<V> {
    V get() throws Exception;
}
