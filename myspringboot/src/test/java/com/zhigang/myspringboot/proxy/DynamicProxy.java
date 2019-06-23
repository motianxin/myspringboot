package com.zhigang.myspringboot.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @program: Code
 * @Description 动态代理
 * @Author zghuang
 * @Date 2019/6/23 23:10
 * @Version 3.2.2
 **/
public class DynamicProxy implements InvocationHandler {

    private Object realObj;

    public DynamicProxy(Object realObj) {
        this.realObj = realObj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("befor excute method: " + method.getName());
        Object result = method.invoke(realObj, args);
        System.out.println("after excute method: " + method.getName());
        return result;
    }

    private static <T> T getProxy(Class<T> tClass, T realObj){
        return (T) Proxy.newProxyInstance(tClass.getClassLoader(), new Class<?>[]{tClass}, new DynamicProxy(realObj));
    }

    public static void main(String[] args) {
        IService realService = new RealService();
        IService proxyService = getProxy(IService.class, realService);
        proxyService.doSomething();
    }
}
