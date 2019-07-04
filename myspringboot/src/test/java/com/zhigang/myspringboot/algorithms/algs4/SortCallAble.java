/**
 * FileName: SortCallAble
 * Author:   Administrator
 * Date:     2019/7/4 20:02
 * Description: sort time call
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.algorithms.algs4;

import com.zhigang.myspringboot.algorithms.stdlib.Stopwatch;

import java.util.concurrent.Callable;

/**
 * 〈sort time call〉
 *
 * @author Administrator
 * @create 2019/7/4 20:02
 * @version 3.2.2
 */
public class SortCallAble implements Callable<Double> {


    private SortClass sortClass;

    private double[] arr;

    public SortCallAble(SortClass sortClass, double[] arr) {
        this.sortClass = sortClass;
        this.arr = arr;
    }

    @Override
    public Double call() throws Exception {

        Stopwatch time = new Stopwatch();

        sortClass.sort(arr);

        return time.elapsedTime();
    }
}
