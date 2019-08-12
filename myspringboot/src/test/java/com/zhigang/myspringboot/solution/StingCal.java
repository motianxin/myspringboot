package com.zhigang.myspringboot.solution;

/**
 * @program: Code
 * @Description 字符串中的不同字符计算
 * @Author admin
 * @Date 2019/8/6 18:54
 * @Version 3.2.2
 **/
public class StingCal {

    public static void calculateStr(String str){
        if (str == null || str.isEmpty()) {
            System.out.println("string is empty!");
            return;
        }
        char a;
        int s;
        while (!str.isEmpty()){
            a = str.charAt(0);
            s = str.length();
            str = str.replaceAll(a +"", "");
            System.out.println(String.format("%s size is %d", a, s - str.length()));
        }
    }

    public static void main(String[] args) {
        String s = "adfgdafgadgdafgadgrthadfadgf235425ytgdagfadg";

        calculateStr(s);
    }

}
