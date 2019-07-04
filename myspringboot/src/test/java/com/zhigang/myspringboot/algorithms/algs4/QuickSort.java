/**
 * FileName: QuickSort
 * Author:   Administrator
 * Date:     2019/7/2 19:57
 * Description: 快速排序
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.algorithms.algs4;


import com.zhigang.myspringboot.algorithms.stdlib.Draw;
import com.zhigang.myspringboot.algorithms.stdlib.StdRandom;

/**
 * 〈快速排序〉
 *
 * @author Administrator
 * @version 3.2.2
 * @create 2019/7/2 19:57
 */
public class QuickSort extends SortClass {


    @Override
    public void sort(double[] arr) {
        super.sort(arr);
        quickSort(arr, 0, arr.length - 1);
    }

    private void quickSort(double[] arr, int lo, int hi) {
        if (hi <= lo) {
            return;
        }
        int pivot = partition(arr, lo, hi);
        quickSort(arr, lo, pivot - 1);
        quickSort(arr, pivot + 1, hi);
    }

    /** 
     * @Description: 快速排序核心方法，得到一个基准下标 
     * @Param: [arr, lo, hi] 
     * @return: int 
     * @Author: admin
     * @Date: 2019/7/3 22:14
     */
    private int partition(double[] arr, int lo, int hi) {
        int left = lo, right = hi;
        int changeTimes = 0;
        while (right > left) {
            if (less(arr[right], arr[left])) {
                swap(arr, right, left);
                changeTimes++;
            }
            if ((changeTimes & 1) ==1){
                left++;
            } else {
                right--;
            }
        }
        return left;
    }

    /**
     * @Description: 指针交换法
     * @Param: [arr, startIndex, endIndex]
     * @return: int
     * @Author: zghuang
     * @Date: 2019/3/27 22:37
     */
    public int partition2(double[] arr, int startIndex, int endIndex) {
        // 取第一个位置的元素作为基准元素
        double pivot = arr[startIndex];
        int left = startIndex;
        int right = endIndex;
        while (left != right) {
            //控制right指针比较并左移

            while (left < right && arr[right] > pivot) {
                right--;
            }
            //控制right指针比较并右移

            while (left < right && arr[left] <= pivot) {
                left++;
            }
            //交换left和right指向的元素

            if (left < right) {
                swap(arr, right, left);
            }
        }
        //pivot和指针重合点交换
        double p = arr[left];
        arr[left] = arr[startIndex];
        arr[startIndex] = p;
        filledChange(arr.length, arr[left], left);
        filledChange(arr.length, arr[startIndex], startIndex);

        return left;
    }


    /**
     * @Author Administrator
     * @Description 单边循环法
     * @Param [arr, startIndex, endIndex]
     * @Return int
     * @date 2019/5/23 20:39
     **/
    public int partition3(double[] arr, int startIndex, int endIndex) {
        int mark = startIndex;
        double pivot = arr[startIndex];
        for (int i = startIndex; i <= endIndex; i++) {
            if (less(arr[i], pivot)) {
                mark++;
                if (mark != i) {
                    swap(arr, i, mark);
                }
            }
        }
        arr[startIndex] = arr[mark];
        arr[mark] = pivot;

        filledChange(arr.length, arr[startIndex], startIndex);
        filledChange(arr.length, arr[mark], mark);

        return mark;
    }

    public static void main(String[] args) {
        SortClass sortClass = new QuickSort();
        double[] dArray = new double[1000];
        for (int i = 0; i < dArray.length; i++) {
            dArray[i] = StdRandom.uniform();
        }
        sortClass.setDraw(new Draw("QuickSort", 300, 300, 1800, 512));
        sortClass.sort(dArray);
    }
}
