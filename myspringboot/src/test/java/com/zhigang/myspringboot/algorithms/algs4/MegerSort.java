/**
 * FileName: MegerSort
 * Author:   Administrator
 * Date:     2019/7/2 19:46
 * Description: 归并排序
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.algorithms.algs4;

/**
 * 〈归并排序〉
 *
 * @author Administrator
 * @create 2019/7/2 19:46
 * @version 3.2.2
 */
public class MegerSort extends SortClass{


    private double[] copyArray;
    @Override
    public void sort(double[] arr) {
        super.sort(arr);
        copyArray = new double[arr.length];
        mergeBUsort(arr);
    }

    public void merge(double[] arr, int lo, int mid, int hi) {
        if (arr[mid] <= arr[mid + 1]) {
            return;
        }
        for (int m = lo; m <= hi; m++) {
            copyArray[m] = arr[m];
        }
        int i = lo;
        int j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                arr[k] = copyArray[j++];
            } else if (j > hi) {
                arr[k] = copyArray[i++];
            } else if (less(copyArray[j], copyArray[i])) {
                arr[k] = copyArray[j++];
            } else {
                arr[k] = copyArray[i++];
            }
            filledChange(arr.length, arr[k], k);
        }
    }

    /**
     * @Description: 归并排序，自顶向下
     * @Param: [arr, lo, hi]
     * @return: void
     * @Author: zghuang
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
     * @Description: 归并排序，自下而上
     * @Param: [arr]
     * @return: void
     * @Author: zghuang
     * @Date: 2019/6/8 23:19
     */
    public void mergeBUsort(double[] arr) {
        int lo = 0, hi = arr.length;
        for (int i = 1; i < hi; i *= 2) {
            for (int j = 0; j < hi - i; j += 2 * i) {
                merge(arr, j, j + i - 1, Math.min(hi - 1, j + 2 * i - 1));
            }
        }
    }

}
