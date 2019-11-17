package com.zhigang.myspringboot.threadtest.childandbase;

/**
 * @program: Code
 * @Description 一句话描述
 * @Author admin
 * @Date 2019/8/13 12:10
 * @Version 3.2.2
 **/
public class Base {
    private static int base = 0;

    static {
        System.out.println("static base method:" + Base.base);
    }

    private int a = 23;

    public Base() {
        printa();

    }

    public void printa() {
        System.out.println("父类的a变量:" + this.a);
    }


}
