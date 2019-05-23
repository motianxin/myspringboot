package com.zhigang.myspringboot.algorithms;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @program: Code
 * @Description 快速排序
 * @Author zghuang
 * @Date 2019/3/27 22:21
 * @Version 3.2.2
 **/
public class QuickSort {
    public static void quickSort(int[] arr, int startIndex, int endIndex) {
        // 递归结束条件：startIndex大等于endIndex的时候
        if (startIndex >= endIndex) {
            return;
        }
        // 得到基准元素位置
        int pivotIndex = partition(arr, startIndex, endIndex);
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
    private static int partition(int[] arr, int startIndex, int endIndex) {
        // 取第一个位置的元素作为基准元素
        int pivot = arr[startIndex];
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
    private static int partition2(int[] arr, int startIndex, int endIndex) {
        // 取第一个位置的元素作为基准元素
        int pivot = arr[startIndex];
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
                int p = arr[left];
                arr[left] = arr[right];
                arr[right] = p;
            }
        }
        //pivot和指针重合点交换
        int p = arr[left];
        arr[left] = arr[startIndex];
        arr[startIndex] = p;
        return left;
    }


    /**
     * @Author Administrator
     * @Description 单边循环法
     * @Param [arr, startIndex, endIndex]
     * @Return int
     * @date 2019/5/23 20:39
     **/
    private static int partition3(int[] arr, int startIndex, int endIndex) {
        int mark = 0;
        int pivot = arr[startIndex];
        for (int i = startIndex; i <= endIndex; i++) {
            if (arr[i] < pivot) {
                mark++;
                int temp = arr[i];
                arr[i] = arr[mark];
                arr[mark] = temp;
            }
        }
        arr[0] = arr[mark];
        arr[mark] = pivot;
        return mark;
    }

    /**
     * @Author Administrator
     * @Description 双边循环优化法
     * @Param [arr, startIndex, endIndex]
     * @Return int
     * @date 2019/5/23 20:40
     **/
    private static int partition4(int[] arr, int startIndex, int endIndex) {
        int left = startIndex;
        int right = endIndex;
        int changeTimes = 0;
        while (right > left) {
            if (arr[right] < arr[left]) {
                int temp = arr[right];
                arr[right] = arr[left];
                arr[left] = temp;
                changeTimes++;
            }
            if (changeTimes % 2 == 1) {
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
    public static void quickSortWithStack(int[] arr, int startIndex, int endIndex) {
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

    public static void main(String[] args) {
        int[] arr = new int[]{4, 5, 3, 6, 9, 2, 7, 8, 1};
        int mark = partition4(arr, 0, arr.length - 1);
        System.out.println(mark);
        Arrays.stream(arr).forEach(System.out::println);
    }

}
