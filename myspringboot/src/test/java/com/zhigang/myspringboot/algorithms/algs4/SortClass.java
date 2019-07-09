package com.zhigang.myspringboot.algorithms.algs4;

import com.zhigang.myspringboot.algorithms.stdlib.Draw;

/**
 * 〈排序父类〉
 *
 * @author admin
 * @create 2019/7/2 19:28
 * @since 3.2.2
 */
public class SortClass {

    /**
     * 画图对象
     */
    private Draw draw;

    public void setDraw(Draw draw) {
        this.draw = draw;
    }

    /**
     * 排序入口
     *
     * @param arr
     */
    protected void sort(double[] arr) {
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            filledRec(n, arr[i], i);
        }
    }

    /**
     * 对比大小
     *
     * @param a
     * @param b
     * @return
     */
    protected boolean less(double a, double b) {
        return a < b;
    }

    /**
     * 交换数组下标元素
     *
     * @param arr
     * @param i
     * @param j
     */
    protected void swap(double[] arr, int i, int j) {
        int n = arr.length;
        double tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
        filledChange(n, arr[i], i);
        filledChange(n, arr[j], j);
    }

    /**
     * 重画直方图
     * @param n
     * @param v
     * @param i
     */
    protected void filledChange(int n, double v, int i) {
        draw.setPenColor(draw.WHITE);
        filledRec(n, 1.0, i);
        draw.setPenColor(draw.BLACK);
        filledRec(n, v, i);
    }

    /**
     * 画直方图
     *
     * @param n
     * @param v
     * @param i
     */
    protected void filledRec(int n, double v, int i) {
        double x = 1.0 * i / n;
        double y = v / 2.0;
        double rw = 0.5 / n;
        double rh = v / 2.0;
        draw.filledRectangle(x, y, rw, rh);
    }

}
