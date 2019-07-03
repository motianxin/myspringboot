/**
 * FileName: ShellSort
 * Author:   Administrator
 * Date:     2019/7/2 19:50
 * Description: shell 排序
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.algorithms.algs4;

/**
 * 〈shell 排序〉
 *
 * @author Administrator
 * @version 3.2.2
 * @create 2019/7/2 19:50
 */
public class ShellSort extends SortClass {


    @Override
    public void sort(double[] arr) {
        super.sort(arr);
        int n = arr.length;
        int h = 1;
        while (h < n / 3) {
            h = 3 * h + 1;
        }
        while (h >= 1) {
            for (int i = h; i < arr.length; i++) {
                for (int j = i; j >= h; j -= h) {
                    if (less(arr[j], arr[j - h])) {
                        swap(arr, j, j - h);
                    }
                }
            }
            h = h / 3;
        }
    }
}
