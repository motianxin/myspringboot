package com.zhigang.myspringboot.proxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @program: Code
 * @Description 一句话描述
 * @Author admin
 * @Date 2019/8/2 21:48
 * @Version 3.2.2
 **/
public class CGLibProxy implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

        System.out.println("cglib proxy begin.");
        Object object = methodProxy.invokeSuper(o, objects);
        System.out.println("cglib proxy end.");
        return object;
    }

    public static <T> T getProxy(Class<T> cla) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(cla);
        enhancer.setCallback(new CGLibProxy());
        return (T) enhancer.create();
    }

    public static void main(String[] args) {
        RealService realService = getProxy(RealService.class);
        realService.doSomething();
        realService.saySomething();
    }
}
