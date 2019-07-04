package com.zhigang.myspringboot.algorithms.algs4;

import com.zhigang.myspringboot.algorithms.stdlib.Draw;

/**
 * 〈排序接口〉
 *
 * @author zghuang
 * @create 2019/7/2 19:28
 * @since 3.2.2
 */
public abstract class SortClass {

    private Draw draw;

    public void setDraw(Draw draw) {
        this.draw = draw;
    }

    protected void sort(double[] arr){
        System.out.println("SortClass.sort");
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            filledRec(n, arr[i], i);
        }
    }

    protected boolean less(double a, double b) {
        return a < b;
    }

    protected void swap(double[] arr, int i, int j) {
        int n = arr.length;
        double tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
        filledChange(n, arr[i], i);
        filledChange(n, arr[j], j);
    }

    protected void filledChange(int n, double v, int i) {
        draw.setPenColor(draw.WHITE);
        filledRec(n, 1.0, i);
        draw.setPenColor(draw.BLACK);
        filledRec(n, v, i);
    }

    protected void filledRec(int n, double v, int i) {
        double x = 1.0 * i / n;
        double y = v / 2.0;
        double rw = 0.5 / n;
        double rh = v / 2.0;
        draw.filledRectangle(x, y, rw, rh);
    }

}
