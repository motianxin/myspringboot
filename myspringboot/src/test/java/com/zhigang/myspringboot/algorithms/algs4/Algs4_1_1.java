package com.zhigang.myspringboot.algorithms.algs4;

import com.zhigang.myspringboot.algorithms.stdlib.StdDraw;
import com.zhigang.myspringboot.algorithms.stdlib.StdRandom;

/**
 * @program: Code
 * @Description 一句话描述
 * @Author zghuang
 * @Date 2019/6/30 16:30
 * @Version 3.2.2
 **/
public class Algs4_1_1 {

    /**
     * @Description: 测试函数值画图
     * @Param: [n]
     * @return: void
     * @Author: admin
     * @Date: 2019/6/30 16:55
     */
    private static void drawFunctionValue(int n) {
        StdDraw.setXscale(0, n);
        StdDraw.setYscale(0, n * n);
        StdDraw.setPenRadius(.01);
        for (int i = 1; i <= n; i++) {
            StdDraw.point(i, i);
            StdDraw.point(i, i * i);
            StdDraw.point(i, i * Math.log(i));
        }
    }


    private static void drawRandomArray(int n) {
        double[] dArray = new double[n];
        for (int i = 0; i < n; i++) {
            dArray[i] = StdRandom.uniform();
        }
        // Arrays.sort(dArray);
        for (int i = 0; i < n; i++) {
            double x = 1.0 * i / n;
            double y = dArray[i] / 2.0;
            double rw = 0.5 / n;
            double rh = dArray[i] / 2.0;
            StdDraw.filledRectangle(x, y, rw, rh);
        }
    }

    private static void drawFG() {
        // StdDraw.setXscale(0, 16);
        // StdDraw.setYscale(0, 16 * 16);
        // StdDraw.setPenRadius(.01);
        int f = 0, g = 1;
        for (int i = 1; i < 16; i++) {
            // StdDraw.point(i, f);
            System.out.println(f);
            f = f + g;
            g = f - g;
        }
    }

    private static String intToBinary(int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = n; i > 0; i = i / 2) {
            sb.append(i % 2);
        }
        return sb.toString();
    }


    public static void main(String[] args) {
        // drawFunctionValue(100);
        // drawRandomArray(100);
        // drawFG();
        System.out.println(intToBinary(89));
    }

}
