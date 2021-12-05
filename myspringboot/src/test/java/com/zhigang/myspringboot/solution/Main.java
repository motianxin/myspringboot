package com.zhigang.myspringboot.solution;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 *
 **/
public class Main {
    public static void main(String[] args) {
        System.out.println(strCalo());
    }

    private static String strCalo() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        scanner.close();
        Map<String, Integer> strToNum = new HashMap<>();
        int begin = 0;
        StringBuilder num = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            if (Character.isDigit(input.charAt(i))) {
                num.append(input.charAt(i));
            } else {
                if (num.length() <= 0) {
                    continue;
                }
                strToNum.put(input.substring(begin, i - num.length()), Integer.parseInt(num.toString()));
                begin = i;
                num = new StringBuilder();
            }
        }
        strToNum.put(input.substring(begin, input.length() - num.length()), Integer.parseInt(num.toString()));
        return strToNum.entrySet().stream().sorted((o1, o2) -> {
            int firstCompare = o1.getValue().compareTo(o2.getValue());
            if (firstCompare == 0) {
                return o1.getKey().compareTo(o2.getKey());
            }
            return firstCompare;
        }).map(entry -> Stream.generate(entry::getKey).limit(entry.getValue()).collect(Collectors.joining()))
            .collect(Collectors.joining());
    }

    private static void extracted() {
        int n = 26;
        List<String> characterList =
            IntStream.range(0, n).mapToObj(i -> (char) ('A' + i) + "").collect(Collectors.toList());
        String[] strings = characterList.toArray(new String[]{});
        System.out.println(Arrays.toString(strings));
        int length = strings.length;
        int num = 2;
        int sum = getSum(n, num);
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

    private static int getSum(int n, int num) {
        int sum = 1;
        for (int i = 0; i < num; i++) {
            sum *= n;
        }
        return sum;
    }
}
