/**
 * FileName: HeapSort
 * Author:   Administrator
 * Date:     2019/7/2 19:29
 * Description: 堆排序
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.algorithms.algs4;

import com.zhigang.myspringboot.algorithms.stdlib.Draw;
import com.zhigang.myspringboot.algorithms.stdlib.StdRandom;

/**
 * 〈堆排序〉
 *
 * @author Administrator
 * @version 3.2.2
 * @create 2019/7/2 19:29
 */
public class HeapSort extends SortClass {


    @Override
    public void sort(double[] arr) {
        super.sort(arr);
        // 取数组长度
        // System.out.println(Arrays.toString(arr));
        int n = arr.length - 1;
        //构造最大堆，重最后一个叶子节点开始依次下沉
        for (int i = (n - 1) / 2; i >= 0; i--) {
            sink(arr, i, n);
        }
        // System.out.println(Arrays.toString(arr));
        while (n > 0) {
            // 交换顶节点和最后一个节点，再下沉，
            // 相当于重顶节点删除一个节点，直到节点删除只剩一个
            swap(arr, 0, n--);
            sink(arr, 0, n);
        }
        // System.out.println(Arrays.toString(arr));
    }

    /**
     * @Description: 叶子节点下沉
     * @Param: [arr, i, n]
     * @return: void
     * @Author: admin
     * @Date: 2019/7/3 21:50
     */
    private void sink(double[] arr, int i, int n) {
        int k = i;
        // n 为数组最后一个x下标， 取<=n
       while (((k << 1) + 1) <= n) {
            // 叶子节点的左节点下标
            int j = (k << 1) + 1;
            // 叶子节点的右节点下标
            int right = j + 1;
            // 如果右节点存在且左节点小于右节点，则取右节点，取子节点中的最大值
            if (right <= n && less(arr[j], arr[right])) {
                j = right;
            }
            // 如果叶子节点大于子节的中的最大节点的值，则不用下沉，跳出循环
            if (less(arr[j], arr[k])) {
                break;
            }
            // 前面没有跳出说明，叶子节点小于其中一个最大值节点，则与最大值节点交换，叶子节点下沉；
            // 节点下标更新为下沉后的节点下标，继续循环下沉
            swap(arr, k, j);
            k = j;
        }
    }

    public static void main(String[] args) {
        SortClass sortClass = new HeapSort();
        double[] dArray = new double[40];
        for (int i = 0; i < dArray.length; i++) {
            dArray[i] = StdRandom.uniform();
        }
        sortClass.setDraw(new Draw("heapSort", 300, 300));
        sortClass.sort(dArray);
    }
}
