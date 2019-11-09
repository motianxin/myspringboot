package com.zhigang.myspringboot.solution;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @program: Code
 * @Description 字符串中的不同字符计算
 * @Author admin
 * @Date 2019/8/6 18:54
 * @Version 3.2.2
 **/
public class StingCal {

    public static void calculateStr(String str) {
        if (str == null || str.isEmpty()) {
            System.out.println("string is empty!");
            return;
        }
        char a;
        int s;
        while (!str.isEmpty()) {
            a = str.charAt(0);
            s = str.length();
            str = str.replaceAll(a + "", "");
            System.out.println(String.format("%s size is %d", a, s - str.length()));
        }
    }

    public static void main(String[] args) {
        int n = 7;
        int[][] a = new int[n][n];
        for (int i = 0; i < n * n; i++) {
            a[i / n][i % n] = i + 1;
        }
        for (int i = 0; i < n; i++) {
            System.out.println(Arrays.toString(a[i]));
        }
        ArrayList<Integer> re = printMatrix(a);

        System.out.println(re.size());
        for (int i = 0; i < n * n; i++) {
            a[i / n][i % n] = re.get(i);
        }
        for (int i = 0; i < n; i++) {
            System.out.println(Arrays.toString(a[i]));
        }
    }

    public static ArrayList<Integer> printMatrix(int[][] matrix) {

        int i = matrix.length;
        int j = matrix[0].length;
        ArrayList<Integer> result = new ArrayList<>(i * j);

        int count = (i + 1) >> 1;
        int m = 0;
        while (m < count) {
            for (int k = m; k < j - m - 1; k++) {
                result.add(matrix[m][k]);
            }
            for (int k = m; k < i - m - 1; k++) {
                result.add(matrix[k][j - m - 1]);
            }
            for (int k = j - m - 1; k > m; k--) {
                result.add(matrix[i - m - 1][k]);
            }
            for (int k = i - m - 1; k > m; k--) {
                result.add(matrix[k][m]);
            }
            if (m == count-1 && (i & 1) == 1) {
                result.add(matrix[i / 2][j / 2]);
            }
            m++;

        }
        return result;
    }

}
