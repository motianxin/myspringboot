/**
 * FileName: QuickSortCompare
 * Author:   Administrator
 * Date:     2019/7/4 16:27
 * Description: 快速排序性能对比
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
 * 〈快速排序性能对比〉
 *
 * @author Administrator
 * @create 2019/7/4 16:27
 * @version 3.2.2
 */
public class QuickSortCompare {

    private static final String[] QUICK_SORT_NAMES = new String[]{"QuickSort", "QuickSortLast"};

    private static final Map<String, SortClass> QUICK_CLASS_MAP = new HashMap<String, SortClass>(){{
        put(QUICK_SORT_NAMES[0], new QuickSort());
        put(QUICK_SORT_NAMES[1], new QuickSortLast());
    }};

    private static final int N = 1000;

    public static void main(String[] args) {
        Draw draw = null;
        SortClass sortClass = null;
        int w = 1900;
        int h = 480;
        double temp;
        List<double[]> doubles = new ArrayList<>(QUICK_SORT_NAMES.length);
        double[] dArray0 = new double[N];
        double[] dArray1 = new double[N];

        for (int i = 0; i < N; i++) {
            temp = StdRandom.uniform();
            dArray0[i] = temp;
            dArray1[i] = temp;
        }
        doubles.add(dArray0);
        doubles.add(dArray1);

        for (int i = 0; i < QUICK_SORT_NAMES.length; i++) {
            draw = new Draw(QUICK_SORT_NAMES[i], 960 *( i/3), 510 * (i % 3), w, h);
            sortClass = QUICK_CLASS_MAP.get(QUICK_SORT_NAMES[i]);
            sortClass.setDraw(draw);
            SortThread sortThread = new SortThread(sortClass, doubles.get(i));
            sortThread.start();
        }
    }
}
