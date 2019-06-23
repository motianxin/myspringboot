package com.zhigang.myspringboot.proxy;

/**
 * @program: Code
 * @Description 一句话描述
 * @Author zghuang
 * @Date 2019/6/23 23:03
 * @Version 3.2.2
 **/
public class RealService implements IService {
    @Override
    public void doSomething() {
        System.out.println("i'm realService.");
    }

    @Override
    public void saySomething() {
        System.out.println("i'm realService, say hello!");
    }
}
