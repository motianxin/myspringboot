package com.zhigang.myspringboot.proxy;

/**
 * @program: Code
 * @Description 静态代理
 * @Author zghuang
 * @Date 2019/6/23 23:05
 * @Version 3.2.2
 **/
public class TraceProxy implements IService {

    private IService realService;

    public TraceProxy(IService realService) {
        this.realService = realService;
    }

    @Override
    public void doSomething() {
        System.out.println("before realService dosomething.");
        realService.doSomething();
        System.out.println("after realservice dosomething");
    }

    @Override
    public void saySomething() {
        System.out.println("before realService saySomething.");
        realService.saySomething();
        System.out.println("after realservice saySomething");
    }

    public static void main(String[] args) {
        IService realService = new RealService();
        IService traceProxy = new TraceProxy(realService);
        traceProxy.doSomething();
        traceProxy.saySomething();
    }

}
