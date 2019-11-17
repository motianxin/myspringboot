/**
 * FileName: SortThread
 * Author:   Administrator
 * Date:     2019/7/2 20:40
 * Description: sortTread
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.algorithms.algs4;

/**
 * 〈sortTread〉
 *
 * @author Administrator
 * @version 3.2.2
 * @create 2019/7/2 20:40
 */
public class SortThread extends Thread {

    private SortClass sortClass;

    private double[] arr;

    public SortThread(SortClass sortClass, double[] arr) {
        this.sortClass = sortClass;
        this.arr = arr;
    }

    @Override
    public void run() {
        sortClass.sort(arr);
    }
}
