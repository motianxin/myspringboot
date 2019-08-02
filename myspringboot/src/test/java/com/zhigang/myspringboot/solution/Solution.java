package com.zhigang.myspringboot.solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @program: Code
 * @Description 一句话描述
 * @Author admin
 * @Date 2019/7/24 20:38
 * @Version 3.2.2
 **/
public class Solution {

    public static int findMaxSubstr(String s) {

        if (s == null || s.length() == 0) {
            return 0;
        }
        int slength = s.length();
        Set<Character> subChats = new HashSet<>(slength);
        boolean flag = true;
        for (int i = slength; i > 1; i--) {
            for (int j = 0; j <= slength - i; j++) {
                for (int k = j; k < j + i; k++) {
                    if (subChats.add(s.charAt(k))) {
                        flag = true;
                    } else {
                        flag = false;
                        subChats.clear();
                        break;
                    }
                }
                if (flag) {
                    return i;
                }
            }

        }
        return 1;
    }

    public static int lengthOfLongestSubstring(String s) {
        int n = s.length(), ans = 0;
        System.out.println("length = " + n);
        int[] index = new int[256]; // current index of character
        // try to extend the range [i, j]
        int charnum;
        for (int j = 0, i = 0; j < n; j++) {
            charnum = s.charAt(j);
            i = index[charnum] > i ? index[charnum] : i;

            ans = ans > j - i + 1 ? ans : j - i + 1;

            System.out.println(String.format("i = %d, j = %d, ans = %d, ", i, j, ans));

            index[charnum] = j + 1;
        }
        System.out.println(Arrays.toString(index));
        return ans;
    }

    public static List<List<Integer>> threeSum(int[] nums) {

        List<List<Integer>> arrayList = new ArrayList<>();
        if (nums == null || nums.length < 3) {
            return arrayList;
        }
        // 数组排序 时间复杂度: O(nLog(n))
        System.out.println(Arrays.toString(nums));
        Arrays.sort(nums);
        System.out.println(Arrays.toString(nums));
        int n = nums.length;
        int a = 0, b, c;
        do {
            // 如果 a[a] = a[a -1],则在i=a-1的时候已经寻找过了，无论找到与否都是重复值
            if ((a > 0 && nums[a] != nums[a - 1]) || a == 0) {
                // a + b + c = 0; a = nums[a], b + c = -a;
                b = a + 1;
                c = n - 1;
                // 数组排序后，nums[c] >= nums[b](b < c)
                int sum;
                while (b < c) {
                    sum = nums[b] + nums[c] + nums[a];
                    if (sum == 0) {
                        arrayList.add(Arrays.asList(nums[a], nums[b], nums[c]));
                        do {
                            b++;
                        } while (b < c && nums[b] == nums[b - 1]);
                        do {
                            c--;
                        } while (b < c && nums[c] == nums[c + 1]);

                    } else if (sum < 0) {
                        // 小于0，b 数小了，需要增大b
                        b++;
                    } else {
                        // 大于0，c 数大了，需要减小c
                        c--;
                    }
                }
            }
            a++;
        } while (nums[a] <= 0 && a < n - 2);
        return arrayList;
    }

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        /**
         如果两个数组的中位数 mid1 < mid2, 则说明合并后的中位数位于 num1.right + num2之间
         否则合并后的中位数位于 nums2.right + nums1 之间 (right 是相对于 mid 而言的)
         getKth 函数负责找到两个数组合并(假设)后有序的数组中的第 k 个元素, k 从 1 开始计算
         **/
        if (nums1.length == 0 && nums2.length == 0) return 0.0;
        int m = nums1.length, n = nums2.length;
        // l: 合并后数组的左半部分的最后一个数 r: 合并后数组的右半部分的第一个数
        int l = (m + n + 1) / 2;
        int r = (m + n + 2) / 2;
        // 如果 m+n 是奇数 getKth 的返回值是相同的, 是偶数则是合并后数组的中间两个数
        if (l == r) return getKth(nums1, 0, nums2, 0, l);
        return (getKth(nums1, 0, nums2, 0, l) + getKth(nums1, 0, nums2, 0, r)) / 2.0;
    }

    private double getKth(int[] nums1, int st1, int[] nums2, int st2, int k) {
        // 边界情况, 如果 nums1数组已经穷尽了, 则只能返回 nums2 中的第 k 个元素
        if (st1 > nums1.length - 1) return nums2[st2 + k - 1];
        if (st2 > nums2.length - 1) return nums1[st1 + k - 1];
        // 边界情况, k = 1 则返回两个数组中最小的那个
        if (k == 1) return Math.min(nums1[st1], nums2[st2]);
        // 在 nums1 和 nums2 当前范围内找出 mid1 和 mid2 判断舍弃哪半部分
        int mid1 = Integer.MAX_VALUE;
        int mid2 = Integer.MAX_VALUE;
        if (st1 + k / 2 - 1 < nums1.length) mid1 = nums1[st1 + k / 2 - 1];
        if (st2 + k / 2 - 1 < nums2.length) mid2 = nums2[st2 + k / 2 - 1];
        // mid1 < mid2 在 nums1.right 和 nums2 之间搜索, 丢掉 k/2 个数.
        if (mid1 < mid2)
            return getKth(nums1, st1 + k / 2, nums2, st2, k - k / 2);
        else
            return getKth(nums1, st1, nums2, st2 + k / 2, k - k / 2);
    }

    public double findMedianSortedArrays2(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        //处理任何一个nums为空数组的情况
        if (m == 0) {
            if (n % 2 != 0)
                return 1.0 * nums2[n / 2];
            return (nums2[n / 2] + nums2[n / 2 - 1]) / 2.0;
        }
        if (n == 0) {
            if (m % 2 != 0)
                return 1.0 * nums1[m / 2];
            return (nums1[m / 2] + nums1[m / 2 - 1]) / 2.0;
        }
        int total = m + n;
        //总数为奇数，找第 total / 2 + 1 个数
        if ((total & 1) == 1) {
            return find_kth(nums1, 0, nums2, 0, total / 2 + 1);
        }
        //总数为偶数，找第 total / 2 个数和第total / 2 + 1个数的平均值
        return (find_kth(nums1, 0, nums2, 0, total / 2) + find_kth(nums1, 0, nums2, 0, total / 2 + 1)) / 2.0;

    }

    //寻找a 和 b 数组中，第k个数字
    double find_kth(int[] a, int a_begin, int[] b, int b_begin, int k) {
        //当a 或 b 超过数组长度，则第k个数为另外一个数组第k个数
        if (a_begin >= a.length)
            return b[b_begin + k - 1];
        if (b_begin >= b.length)
            return a[a_begin + k - 1];
        //k为1时，两数组最小的那个为第一个数
        if (k == 1)
            return Math.min(a[a_begin], b[b_begin]);

        int mid_a = Integer.MAX_VALUE;
        int mid_b = Integer.MAX_VALUE;
        //mid_a / mid_b 分别表示 a数组、b数组中第 k / 2 个数
        if (a_begin + k / 2 - 1 < a.length)
            mid_a = a[a_begin + k / 2 - 1];
        if (b_begin + k / 2 - 1 < b.length)
            mid_b = b[b_begin + k / 2 - 1];
        //如果a数组的第 k / 2 个数小于b数组的第 k / 2 个数，表示总的第 k 个数位于 a的第k / 2个数的后半段，或者是b的第 k / 2个数的前半段
        //由于范围缩小了 k / 2 个数，此时总的第 k 个数实际上等于新的范围内的第 k - k / 2个数，依次递归
        if (mid_a < mid_b)
            return find_kth(a, a_begin + k / 2, b, b_begin, k - k / 2);
        //否则相反
        return find_kth(a, a_begin, b, b_begin + k / 2, k - k / 2);
    }


    public static void main(String[] args) {

        Random random = new Random();
        String s2 = Stream.generate(() -> ((char) ('a' + random.nextInt(26))) + "")
                .limit(60)
                .collect(Collectors.joining());
        String s = "abcdefghijklmdfg";
        /*int[] a = new int[50];
        for (int i = 0; i < a.length; i++) {
            a[i] = random.nextInt(200) - 100;
        }
        List<List<Integer>> arrayList = threeSum(a);

        System.out.println(arrayList);*/

        System.out.println(s);
        System.out.println(lengthOfLongestSubstring(s));
    }


}
