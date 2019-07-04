/**
 * FileName: QuickSortLast
 * Author:   Administrator
 * Date:     2019/7/4 16:16
 * Description: last
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.algorithms.algs4;

/**
 * 〈last〉
 *
 * @author Administrator
 * @create 2019/7/4 16:16
 * @version 3.2.2
 */
public class QuickSortLast extends SortClass {
    @Override
    protected void sort(double[] arr) {
        super.sort(arr);
        quickSort(arr, 0, arr.length -1);
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
}



