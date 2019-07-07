package com.zhigang.myspringboot.algorithms.algs4;

import com.zhigang.myspringboot.algorithms.stdlib.StdDraw;

/**
 * @program: Code
 * @Description 一句话描述
 * @Author admin
 * @Date 2019/7/6 17:10
 * @Version 3.2.2
 **/
public class DrawNode {

    private static final int ARRAY_LENGTH = 58;

    /**
     * @Description: 根据序号得到该值所在的层数
     * @Param: [n]
     * @return: int
     * @Author: admin
     * @Date: 2019/7/6 17:24
     */
    public static int getNumPower(int n) {
        // int temp = n + 1;
        int tmp = 31 - Integer.numberOfLeadingZeros(n + 1);
        // System.out.println(" n  = " + temp + ", tmp = " + tmp + ", 1<<tmp = " + (1 << tmp));
        return tmp;
    }

    /**
     * @Description: 计算值所在层的列数
     * @Param: [n]
     * @return: int
     * @Author: admin
     * @Date: 2019/7/6 17:28
     */
    public static int getColum(int n) {
        return n + 1 - (1 << getNumPower(n));
    }

    /**
     * @Description: 计算最底层的个数
     * @Param: [n]
     * @return: int
     * @Author: admin
     * @Date: 2019/7/6 17:31
     */
    public static int maxNumber() {
        return 1 << getNumPower(ARRAY_LENGTH);
    }

    /**
     * @Description: Y方向层间隔
     * @Param: []
     * @return: double
     * @Author: admin
     * @Date: 2019/7/6 19:51
     */
    public static double interval(double d) {
        // 最大层数
        int maxR = getNumPower(ARRAY_LENGTH) + 1;
        //层间隔
        double interval = (1.0 - maxR * d) / (maxR - 1);
        return interval;
    }

    /**
     * @Description: 计算节点直径
     * @Param: [n]
     * @return: double
     * @Author: admin
     * @Date: 2019/7/6 17:33
     */
    public static double getDiameter() {
        return 1.0 / (maxNumber() * 2 - 1);
    }

    /**
     * @Description: 得到x坐标值
     * @Param: [n]
     * @return: double
     * @Author: admin
     * @Date: 2019/7/6 18:36
     */
    public static double getScaleX(int n) {
        if (n == 0) {
            return 0.5;
        }
        // 当前行和最后一行的间隔值
        int devi = getNumPower(ARRAY_LENGTH) - getNumPower(n);
        //节点直径
        double d = getDiameter();
        //当前行所在的序号，重 0 开始
        int x = getColum(n);
        double r = d / 2;
        //当前行第一个节点与两端的距离
        double left = d * Math.pow(2.0, devi) - d;
        //当前行的最多节点个数 2^(n)
        int number = 1 << getNumPower(n);
        //当前行每两个节点间的间隔距离
        double interval = (1.0 - 2 * left - d * number) / (number - 1);
        // 当前行每个节点的 x 坐标值
        return left + x * interval + d * x + r;
    }

    public static double getScaleY(int n) {
        double diameter = getDiameter();
        //层间隔
        double interval = interval(diameter);

        int r = getNumPower(n);
        return 1.0 - (0.5 * diameter + r * interval + diameter * r);
    }

    public static void main(String[] args) {
        if (ARRAY_LENGTH == 0) {
            StdDraw.filledCircle(.5, .5, .5);
        }
        double diameter = getDiameter();
        for (int i = 0; i <= ARRAY_LENGTH; i++) {
            double x = getScaleX(i);
            double y = getScaleY(i);
            if ((getNumPower(i) & 1) == 0) {
                StdDraw.setPenColor(StdDraw.BLACK);
            } else {
                StdDraw.setPenColor(StdDraw.RED);
            }
            // System.out.println(" x = " + x + ", y = " + y);
            StdDraw.filledCircle(x, y, diameter / 2);
            if (2 * i + 1 <= ARRAY_LENGTH) {
                StdDraw.line(x, y, getScaleX(2 * i + 1), getScaleY(2 * i + 1));
            }
            if (2 * i + 2 <= ARRAY_LENGTH) {
                StdDraw.line(x, y, getScaleX(2 * i + 2), getScaleY(2 * i + 2));
            }
        }
    }


}
