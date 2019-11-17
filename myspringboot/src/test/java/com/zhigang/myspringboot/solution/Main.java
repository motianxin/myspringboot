package com.zhigang.myspringboot.solution;

import java.io.IOException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/**
 * @program: Code
 * @Description 一句话描述
 * @Author admin
 * @Date 2019/8/21 18:27
 * @Version 3.2.2
 **/
public class Main {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        int size = sc.nextInt();
        Set<Integer> numbers = new TreeSet<>();
        for (int i = 0; i < size; i++) {
            numbers.add(sc.nextInt());
        }
        numbers.forEach((i) -> System.out.println(i));
        sc.close();
    }
}
