/**
 * FileName: AllSortCompare
 * Author:   Administrator
 * Date:     2019/7/2 19:20
 * Description: 所有排序算法演示
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.algorithms.algs4;

import com.zhigang.myspringboot.algorithms.stdlib.Draw;
import com.zhigang.myspringboot.algorithms.stdlib.StdRandom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈所有排序算法演示〉
 *
 * @author Administrator
 * @version 3.2.2
 * @create 2019/7/2 19:20
 */
public class AllSortCompare {

    public static final String[] SORT_NAMES = new String[]{"quickSort", "shellSort", "insertSort", "mergeSort", "bubbleSort", "heapSort"};

    public static final Map<String, SortClass> CLASS_MAP = new HashMap<String, SortClass>(){{
        put(SORT_NAMES[0], new QuickSort());
        put(SORT_NAMES[1], new ShellSort());
        put(SORT_NAMES[2], new InsertSort());
        put(SORT_NAMES[3], new MergeSort());
        put(SORT_NAMES[4], new BubbleSort());
        put(SORT_NAMES[5], new HeapSort());
    }};

    private static final int N = 400;

    public static void main(String[] args) {
        Draw draw = null;
        SortClass sortClass = null;
        double temp;
        List<double[]> doubles = new ArrayList<>(SORT_NAMES.length);
        double[] dArray0 = new double[N];
        double[] dArray1 = new double[N];
        double[] dArray2 = new double[N];
        double[] dArray3 = new double[N];
        double[] dArray4 = new double[N];
        double[] dArray5 = new double[N];

        for (int i = 0; i < N; i++) {
            temp = StdRandom.uniform();
            dArray0[i] = temp;
            dArray1[i] = temp;
            dArray2[i] = temp;
            dArray3[i] = temp;
            dArray4[i] = temp;
            dArray5[i] = temp;
        }
        doubles.add(dArray0);
        doubles.add(dArray2);
        doubles.add(dArray3);
        doubles.add(dArray1);
        doubles.add(dArray4);
        doubles.add(dArray5);
        for (int i = 0; i < SORT_NAMES.length; i++) {
            draw = new Draw(SORT_NAMES[i], 960 *( i/3), 350 * (i % 3));
            sortClass = CLASS_MAP.get(SORT_NAMES[i]);
            sortClass.setDraw(draw);
            SortThread sortThread = new SortThread(sortClass, doubles.get(i));
            sortThread.start();
        }

    }


}
