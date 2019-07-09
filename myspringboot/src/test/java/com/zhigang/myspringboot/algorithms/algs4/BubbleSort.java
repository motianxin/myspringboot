/**
 * FileName: BubbleSort
 * Author:   Administrator
 * Date:     2019/7/2 20:36
 * Description: 冒泡排序
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.algorithms.algs4;

/**
 * 〈冒泡排序〉
 *
 * @author Administrator
 * @create 2019/7/2 20:36
 * @version 3.2.2
 */
public class BubbleSort extends SortClass {
    @Override
    public void sort(double[] array) {
        super.sort(array);
        // 标记无序边界
        int lastExchange = 0;
        int sortBorder = array.length - 1;
        for (int i = 0; i < array.length - 1; i++) {
            // 标记一轮排序后是否有序
            boolean isSorted = true;
            for (int j = 0; j < sortBorder; j++) {
                if (less(array[j + 1], array[j])) {
                    swap(array, j, j + 1);
                    // 有交换说明无序，没有交换说明有序
                    isSorted = false;
                    // 更新无序边界
                    lastExchange = j;
                }
            }
            sortBorder = lastExchange;
            if (isSorted) {
                break;
            }
        }
    }
}
