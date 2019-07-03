/**
 * FileName: HeapSort
 * Author:   Administrator
 * Date:     2019/7/2 19:29
 * Description: 堆排序
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.algorithms.algs4;

/**
 * 〈堆排序〉
 *
 * @author Administrator
 * @create 2019/7/2 19:29
 * @version 3.2.2
 */
public class HeapSort extends SortClass {


    @Override
    public void sort(double[] arr) {
        super.sort(arr);

        int n = arr.length -1;
        for (int i = n / 2; i >= 1; i--) {
            sink(arr, i, n);
        }
        while (n > 1) {
            swap(arr, 1, n--);
            sink(arr, 1, n);
        }

        for (int i = 0; i < arr.length -1; i++) {
            if (less(arr[i], arr[i + 1])) {
                break;
            } else {
                swap(arr, i, i +1);
            }
        }



    }

    private void sink(double[] arr, int i, int n) {
        int k = i;
        while (k<<1 <= n) {
            int j = k <<1;
            if (j < n && less(arr[j], arr[j +1])) {
                j++;
            }
            if (!less(arr[k], arr[j])) {
                break;
            }
            swap(arr, k, j);
            k = j;
        }
    }
}
