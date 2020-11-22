package com.zhigang.myspringboot.solution;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @program: Code
 * @Description 一句话描述
 * @Author admin
 * @Date 2019/8/21 18:27
 * @Version 3.2.2
 **/
public class Main {
    public static void main(String[] args) {
        int n = 26;
        List<String> characterList = IntStream.range(0, n)
            .mapToObj(i -> (char)('A' + i) + "")
            .collect(Collectors.toList());
        String[] strings = characterList.toArray(new String[]{});
        System.out.println(Arrays.toString(strings));
        int length = strings.length;
        int num = 2;
        int sum = getsum(n, num);
        System.out.println("sum = " + sum);
        for (int i = 0; i < sum; i++) {
            int id = i % length;
            if (id == 0) {
                System.out.println("i = " + i + ", index[0] = " + (i / length) + ", index[1] = " + id);
                swap(strings, 0, i / length);
            } else {
                System.out.print(strings[0] + strings[id] + ", ");
            }
        }

    }

    private static void swap(String[] strings, int i, int id) {
        String temp = strings[i];
        strings[i] = strings[id];
        strings[id] = temp;
    }

    private static int getsum(int n, int num) {
        int sum = 1;
        for (int i = 0; i < num; i++) {
            sum *= n;
        }
        return sum;
    }
}
