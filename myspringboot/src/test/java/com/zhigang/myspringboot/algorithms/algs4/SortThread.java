/**
 * FileName: SortThread
 * Author:   Administrator
 * Date:     2019/7/2 20:40
 * Description: sortTread
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.algorithms.algs4;

import com.zhigang.myspringboot.algorithms.stdlib.StdRandom;

/**
 * 〈sortTread〉
 *
 * @author Administrator
 * @create 2019/7/2 20:40
 * @version 3.2.2
 */
public class SortThread extends Thread{

    private SortClass sortClass;

    private double[] arr;

    public SortThread(SortClass sortClass, double[] arr){
        this.sortClass = sortClass;
        this.arr = arr;
    }

    @Override
    public void run() {
        int n = arr.length;

        for (int i = 0; i < n; i++) {
            sortClass.filledRec(n, arr[i], i);
        }
        sortClass.sort(arr);
    }
}
