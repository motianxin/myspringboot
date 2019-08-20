package com.zhigang.myspringboot.threadtest.childandbase;

/**
 * @program: Code
 * @Description 一句话描述
 * @Author admin
 * @Date 2019/8/13 12:17
 * @Version 3.2.2
 **/
public class Child extends Base {

    private int a = 8;

    private static int child = 9;

    static {
        System.out.println("child static method:" + child);
    }

    public Child() {
        printa();
    }

    @Override
    public void printa() {
        // super.printa();
        System.out.println("child a is :" + a);
    }

    public static void main(String[] args) {
        new Child();
    }
}
