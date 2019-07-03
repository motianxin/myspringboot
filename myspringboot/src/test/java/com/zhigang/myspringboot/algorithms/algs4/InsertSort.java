/**
 * FileName: InsertSort
 * Author:   Administrator
 * Date:     2019/7/2 19:49
 * Description: 插入排序
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.algorithms.algs4;

/**
 * 〈插入排序〉
 *
 * @author Administrator
 * @create 2019/7/2 19:49
 * @version 3.2.2
 */
public class InsertSort extends SortClass {


    @Override
    public void sort(double[] arr) {
        super.sort(arr);
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            for (int j = i; j >= 1; j--) {
                // 插入排序，如果后面的值比前面的小则插入到前面，
                // 直到遇到一个更小的值或者到首元素
                if (less(arr[j], arr[j - 1])) {
                    swap(arr, j, j - 1);
                }
            }
        }
    }
}
