package com.zhigang.myspringboot.algorithms.algs4;

import com.zhigang.myspringboot.algorithms.stdlib.StdDraw;
import com.zhigang.myspringboot.algorithms.stdlib.StdRandom;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @program: Code
 * @Description 一句话描述
 * @Author zghuang
 * @Date 2019/6/30 16:30
 * @Version 3.2.2
 **/
public class Algs4_1_1 {
    private static final int N = 400;
    private static double[] copyArray = new double[N];

    public static void quickSort(double[] arr, int startIndex, int endIndex) {
        // 递归结束条件：startIndex大等于endIndex的时候
        if (startIndex >= endIndex) {
            return;
        }
        // 得到基准元素位置
        int pivotIndex = partition4(arr, startIndex, endIndex);
        // 用分治法递归数列的两部分
        quickSort(arr, startIndex, pivotIndex - 1);
        quickSort(arr, pivotIndex + 1, endIndex);
    }

    /**
     * @Description: 挖坑法
     * @Param: [arr, startIndex, endIndex]
     * @return: int
     * @Author: zghuang
     * @Date: 2019/3/27 22:37
     */
    public static int partition(double[] arr, int startIndex, int endIndex) {
        // 取第一个位置的元素作为基准元素
        double pivot = arr[startIndex];
        int left = startIndex;
        int right = endIndex;
        // 坑的位置，初始等于pivot的位置
        int index = startIndex;
        //大循环在左右指针重合或者交错时结束
        while (right >= left) {
            //right指针从右向左进行比较
            while (right >= left) {
                if (arr[right] < pivot) {
                    arr[left] = arr[right];
                    index = right;
                    left++;
                    break;
                }
                right--;
            }
            //left指针从左向右进行比较
            while (right >= left) {
                if (arr[left] > pivot) {
                    arr[right] = arr[left];
                    index = left;
                    right--;
                    break;
                }
                left++;
            }
        }
        arr[index] = pivot;
        return index;
    }

    /**
     * @Description: 指针交换法
     * @Param: [arr, startIndex, endIndex]
     * @return: int
     * @Author: zghuang
     * @Date: 2019/3/27 22:37
     */
    public static int partition2(double[] arr, int startIndex, int endIndex) {
        // 取第一个位置的元素作为基准元素
        double pivot = arr[startIndex];
        int left = startIndex;
        int right = endIndex;
        while (left != right) {
            //控制right指针比较并左移

            while (left < right && arr[right] > pivot) {
                right--;
            }
            //控制right指针比较并右移

            while (left < right && arr[left] <= pivot) {
                left++;
            }
            //交换left和right指向的元素

            if (left < right) {
                swap(arr, right, left);
            }
        }
        //pivot和指针重合点交换
        double p = arr[left];
        arr[left] = arr[startIndex];
        arr[startIndex] = p;
        filledChange(arr.length, arr[left], left);
        filledChange(arr.length, arr[startIndex], startIndex);

        return left;
    }


    /**
     * @Author Administrator
     * @Description 单边循环法
     * @Param [arr, startIndex, endIndex]
     * @Return int
     * @date 2019/5/23 20:39
     **/
    public static int partition3(double[] arr, int startIndex, int endIndex) {
        int mark = startIndex;
        double pivot = arr[startIndex];
        for (int i = startIndex; i <= endIndex; i++) {
            if (less(arr[i], pivot)) {
                mark++;
                swap(arr, i, mark);
            }
        }
        arr[startIndex] = arr[mark];
        arr[mark] = pivot;

        filledChange(arr.length, arr[startIndex], startIndex);
        filledChange(arr.length, arr[mark], mark);

        return mark;
    }

    /**
     * @Author Administrator
     * @Description 双边循环优化法
     * @Param [arr, startIndex, endIndex]
     * @Return int
     * @date 2019/5/23 20:40
     **/
    public static int partition4(double[] arr, int startIndex, int endIndex) {
        int left = startIndex;
        int right = endIndex;
        int changeTimes = 0;
        while (right > left) {
            if (less(arr[right], arr[left])) {
                swap(arr, right, left);
                changeTimes++;
            }
            if ((changeTimes & 1) == 1) {
                left++;
            } else {
                right--;
            }
        }
        return left;
    }

    /**
     * @Description: 用栈实现，非递归
     * @Param: [arr, startIndex, endIndex]
     * @return: void
     * @Author: zghuang
     * @Date: 2019/3/27 22:45
     */
    public static void quickSortWithStack(double[] arr, int startIndex, int endIndex) {
        // 用一个集合栈来代替递归的函数栈
        Stack<Map<String, Integer>> quickSortStack = new Stack<>();
        // 整个数列的起止下标，以哈希的形式入栈
        Map rootParam = new HashMap();
        rootParam.put("startIndex", startIndex);
        rootParam.put("endIndex", endIndex);
        quickSortStack.push(rootParam);
        // 循环结束条件：栈为空时结束
        while (!quickSortStack.isEmpty()) {
            // 栈顶元素出栈，得到起止下标
            Map<String, Integer> param = quickSortStack.pop();
            // 得到基准元素位置
            int pivotIndex = partition(arr, param.get("startIndex"), param.get("endIndex"));
            // 根据基准元素分成两部分, 把每一部分的起止下标入栈
            if (param.get("startIndex") < pivotIndex - 1) {
                Map<String, Integer> leftParam = new HashMap<>();
                leftParam.put("startIndex", param.get("startIndex"));
                leftParam.put("endIndex", pivotIndex - 1);
                quickSortStack.push(leftParam);
            }
            if (pivotIndex + 1 < param.get("endIndex")) {
                Map<String, Integer> rightParam = new HashMap<>();
                rightParam.put("startIndex", pivotIndex + 1);
                rightParam.put("endIndex", param.get("endIndex"));
                quickSortStack.push(rightParam);
            }
        }
    }

    /**
     * @Description: 希尔排序
     * @Param: [arr]
     * @return: void
     * @Author: zghuang
     * @Date: 2019/6/8 17:36
     */
    public static void shellSort(double[] arr) {
        int n = arr.length;
        int h = 1;
        // h = 1,4,13,40 ....;
        while (h < n / 3) {
            h = 3 * h + 1;
        }

        while (h >= 1) {
            int compareTimes = 0;
            for (int i = h; i < n; i++) {
                for (int j = i; j >= h; j -= h) {
                    compareTimes++;
                    if (less(arr[j], arr[j - h])) {
                        swap(arr, j, j - h);
                    }
                }
            }
            h = h / 3;
        }
    }


    public static void merge(double[] arr, int lo, int mid, int hi) {
        if (arr[mid] <= arr[mid + 1]) {
            return;
        }
        for (int i = lo; i <= hi; i++) {
            copyArray[i] = arr[i];
        }
        int i = lo;
        int j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                arr[k] = copyArray[j++];
            } else if (j > hi) {
                arr[k] = copyArray[i++];
            } else if (less(copyArray[j], copyArray[i])) {
                arr[k] = copyArray[j++];
            } else {
                arr[k] = copyArray[i++];
            }
            filledChange(arr.length, arr[k], k);
        }
    }

    /**
     * @Description: 归并排序，自顶向下
     * @Param: [arr, lo, hi]
     * @return: void
     * @Author: zghuang
     * @Date: 2019/6/8 23:12
     */
    public static void mergeSort(double[] arr, int lo, int hi) {
        if (hi <= lo) {
            return;
        }
        int mid = lo + (hi - lo) / 2;
        mergeSort(arr, lo, mid);
        mergeSort(arr, mid + 1, hi);
        merge(arr, lo, mid, hi);
    }

    /**
     * @Description: 归并排序，自下而上
     * @Param: [arr]
     * @return: void
     * @Author: zghuang
     * @Date: 2019/6/8 23:19
     */
    public static void mergeBUsort(double[] arr) {
        int lo = 0, hi = arr.length;
        for (int i = 1; i < hi; i *= 2) {
            for (int j = 0; j < hi - i; j += 2 * i) {
                merge(arr, j, j + i - 1, Math.min(hi - 1, j + 2 * i - 1));
            }
        }
    }

    /**
     * @Description: bubble Sort
     * @Param: [array]
     * @return: void
     * @Author: zghuang
     * @Date: 2019/6/8 17:26
     */
    public static void bubbleSort(double[] array) {
        if (array.length <= 1) {
            return;
        }
        // 标记无序边界
        int lastExchange = 0;
        int sortBorder = array.length - 1;
        for (int i = 0; i < array.length - 1; i++) {
            // 标记一轮排序后是否有序
            boolean isSorted = true;
            for (int j = 0; j < sortBorder; j++) {
                int tmp = 0;
                if (less(array[j], array[j + 1])) {
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

    /** 
     * @Description: 堆排序
     * @Param: [arr] 
     * @return: void 
     * @Author: admin
     * @Date: 2019/7/1 22:02
     */
    public static void heapSort(double[] arr) {

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

    private static void sink(double[] arr, int i, int n) {
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

    private static void filledChange(int n, double v, int i) {
        StdDraw.setPenColor(StdDraw.WHITE);
        filledRec(n, 1.0, i);
        StdDraw.setPenColor(StdDraw.BLACK);
        filledRec(n, v, i);
    }

    private static void filledRec(int n, double v, int i) {
        double x = 1.0 * i / n;
        double y = v / 2.0;
        double rw = 0.5 / n;
        double rh = v / 2.0;
        StdDraw.filledRectangle(x, y, rw, rh);
    }

    /**
     * @Description: Insert sort
     * @Param: [arr]
     * @return: void
     * @Author: zghuang
     * @Date: 2019/6/8 17:35
     */
    public static void insertSort(double[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            for (int j = i; j > 0 && less(arr[j], arr[j - 1]); j--) {
                swap(arr, j, j - 1);
            }
        }
    }


    /**
     * @Description: Comparison of size
     * @Param: [a, b]
     * @return: boolean
     * @Author: zghuang
     * @Date: 2019/6/8 17:21
     */
    public static boolean less(double a, double b) {
        return a < b;
    }

    /**
     * @Description: Exchange element
     * @Param: [arr, i, j]
     * @return: void
     * @Author: zghuang
     * @Date: 2019/6/8 17:21
     */
    public static void swap(double[] arr, int i, int j) {
        int n = arr.length;
        double tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
        filledChange(n, arr[i], i);
        filledChange(n, arr[j], j);
    }

    /**
     * @Description: 测试函数值画图
     * @Param: [n]
     * @return: void
     * @Author: admin
     * @Date: 2019/6/30 16:55
     */
    private static void drawFunctionValue(int n) {
        StdDraw.setXscale(0, n);
        StdDraw.setYscale(0, n * n);
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius(.01);
        for (int i = 1; i <= n; i++) {
            StdDraw.point(i, i);
            StdDraw.point(i, i * i);
            StdDraw.point(i, i * Math.log(i));
        }
    }


    /**
     * @Description: 各种排序轨迹动态演示
     * @Param: [n]
     * @return: void
     * @Author: admin
     * @Date: 2019/7/1 18:23
     */
    private static void drawRandomArray(int n) {
        double[] dArray = new double[n];
        for (int i = 0; i < n; i++) {
            dArray[i] = StdRandom.uniform();
        }

        for (int i = 0; i < n; i++) {
            filledRec(n, dArray[i], i);
        }
        copyArray = new double[n];
        // bubbleSort(dArray);
        // insertSort(dArray);
        // quickSort(dArray, 0, dArray.length - 1);
        mergeSort(dArray, 0, n-1);
        // mergeBUsort(dArray);
        // shellSort(dArray);
        // heapSort(dArray);

    }



    private static void drawFG() {
        int f = 0, g = 1;
        for (int i = 1; i < 16; i++) {
            System.out.println(f);
            f = f + g;
            g = f - g;
        }
    }

    private static String intToBinary(int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = n; i > 0; i = i / 2) {
            sb.append(i % 2);
        }
        return sb.reverse().toString();
    }


    public static void main(String[] args) {
        // drawFunctionValue(100);
        drawRandomArray(N);
        // drawFG();
        // System.out.println(intToBinary(100));
    }

}
