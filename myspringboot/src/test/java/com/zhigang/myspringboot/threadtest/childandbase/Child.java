package com.zhigang.myspringboot.threadtest.childandbase;

/**
 * @program: Code
 * @Description 一句话描述
 * @Author admin
 * @Date 2019/8/13 12:17
 * @Version 3.2.2
 **/
public class Child extends Base {

    private static int child = 9;

    static {
        System.out.println("child static method:" + Child.child);
    }

    private int a = 8;

    public Child() {
        printa();
    }

    public static void main(String[] args) {
        new Child();
    }

    @Override
    public void printa() {
        // super.printa();
        System.out.println("child a is :" + this.a);
    }
}
