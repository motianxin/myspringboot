/**
 * FileName: MergeSort
 * Author:   Administrator
 * Date:     2019/7/2 19:46
 * Description: 归并排序
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.algorithms.algs4;

import com.zhigang.myspringboot.algorithms.stdlib.Draw;
import com.zhigang.myspringboot.algorithms.stdlib.StdRandom;

/**
 * 〈归并排序〉
 *
 * @author Administrator
 * @version 3.2.2
 * @create 2019/7/2 19:46
 */
public class MergeSort extends SortClass {


    private double[] copyArray;

    public static void main(String[] args) {
        SortClass sortClass = new MergeSort();
        double[] dArray = new double[400];
        for (int i = 0; i < dArray.length; i++) {
            dArray[i] = StdRandom.uniform();
        }
        sortClass.setDraw(new Draw("MergeSort", 300, 300));
        sortClass.sort(dArray);
    }

    @Override
    public void sort(double[] arr) {
        super.sort(arr);
        copyArray = new double[arr.length];
        // mergeSort(arr, 0, arr.length -1);
        mergeBUsort(arr);
    }

    /**
     * @Description: 左右归并排序，mid下标分成两边有序数组归并
     * @Param: [arr, lo, mid, hi]
     * @return: void
     * @Author: admin
     * @Date: 2019/7/3 22:32
     */
    public void merge(double[] arr, int lo, int mid, int hi) {
        // System.out.println("left= " + lo + ", mid=" + mid +", right=" + hi);
        if (less(arr[mid], arr[mid + 1])) {
            return;
        }
        for (int m = lo; m <= hi; m++) {
            //拷贝一份数组用于左右比较
            copyArray[m] = arr[m];
        }
        int left = lo;
        int right = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (left > mid) {
                // 左边的元素用完了，用右边的
                arr[k] = copyArray[right++];
            } else if (right > hi) {
                // 右边的元素用完了用左边的
                arr[k] = copyArray[left++];
            } else if (less(copyArray[right], copyArray[left])) {
                //如果右边的元素小于左边，取右边的元素。右下标右移
                arr[k] = copyArray[right++];
            } else {
                //如果左边的元素小于右边，取左边元素，左下标右移
                arr[k] = copyArray[left++];
            }
            filledChange(arr.length, arr[k], k);
        }
    }

    /**
     * @Description: 归并排序，自顶向下.递归方式
     * @Param: [arr, lo, hi]
     * @return: void
     * @Author: admin
     * @Date: 2019/6/8 23:12
     */
    public void mergeSort(double[] arr, int lo, int hi) {
        if (hi <= lo) {
            return;
        }
        int mid = lo + (hi - lo) / 2;
        mergeSort(arr, lo, mid);
        mergeSort(arr, mid + 1, hi);
        merge(arr, lo, mid, hi);
    }

    /**
     * @Description: 归并排序，自下而上，循环方式
     * @Param: [arr]
     * @return: void
     * @Author: admin
     * @Date: 2019/6/8 23:19
     */
    public void mergeBUsort(double[] arr) {
        int lo = 0, hi = arr.length;
        // i= 2^n (n <= log2 hi)
        // i表示归并的元素个数。值从1开始，以2的幂递增，不超过数组长度
        for (int i = 1; i < hi; i *= 2) {
            //j 为下标值，最左边下标从0开始，每一组+i，不超过长度-i
            for (int j = lo; j < hi - i; j += 2 * i) {
                // j为左下标，j+i-1为中间值，中间值到j+2*i-1为右下标，-1是因为下标从0开始
                merge(arr, j, j + i - 1, Math.min(hi - 1, j + 2 * i - 1));
            }
        }
    }

}
