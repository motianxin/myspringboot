package com.zhigang.myspringboot.solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.stream.IntStream;

/**
 * @program: Code
 * @Description 一句话描述
 * @Author admin
 * @Date 2019/7/24 20:38
 * @Version 3.2.2
 **/
public class Solution {

    public static void main(String[] args) {
        Random random = new Random();
        int[] height = new int[10];
        for (int i = 0; i < 10; i++) {
            height[i] = random.nextInt(1000);
        }
        System.out.println("numOfMaintenSeen = " + numOfMaintenSeen(height));
    }

    private static int numOfMaintenSeen(int[] array) {
        int len = array.length;
        if (len < 3) {
            return 0;
        }
        int[] result = new int[len];
        int left = getLeft(array, len, result);
        int right = getRight(array, len, result);
        return left + right;
    }

    private static int getRight(int[] array, int len, int[] rightResult) {
        reverseArray(array, len / 2);
        calNum(array, len, rightResult);
        int right = 0;
        for (int i = len - 1; i >= 0; i -= 2) {
            right += rightResult[i];
        }
        return right;
    }

    private static int getLeft(int[] array, int len, int[] result) {
        calNum(array, len, result);
        return IntStream.of(result).filter(i -> (i & 1) == 1).sum();
    }

    private static void reverseArray(int[] array, int i) {
        for (int j = 0; j <= i; j++) {
            int temp = array[j];
            array[j] = array[array.length - 1 - j];
            array[array.length - 1 - j] = temp;
        }
    }

    private static void calNum(int[] array, int len, int[] result) {
        result[1] = 1;
        int[] firstMax = new int[len];
        if (array[0] < array[1]) {
            firstMax[1] = 1;
        }
        for (int i = 2; i < len; i++) {
            if (array[i - 1] > array[i]) {
                firstMax[i] = i - 1;
            } else {
                setFirstMax(array, firstMax, i);
            }
            setResult(result, firstMax, i);
        }
        System.out.println("arrays = " + Arrays.toString(array));
        System.out.println("firstMax = " + Arrays.toString(firstMax));
        System.out.println("result = " + Arrays.toString(result));
    }

    private static void setResult(int[] result, int[] firstMax, int i) {
        if (firstMax[i] == i) {
            result[i] = i;
            return;
        }
        int index = firstMax[i];
        int sum = i - index;
        while (firstMax[index] != index) {
            sum++;
            index = firstMax[index];
        }
        result[i] = sum;
    }

    private static void setFirstMax(int[] array, int[] firstMax, int i) {
        // arrays[i -1] < arrays[i]
        int index = i - 1;
        int height = array[i];
        while (index >= 0) {
            if (array[index] > height) {
                firstMax[i] = index;
                return;
            }
            if (index == firstMax[index]) {
                firstMax[i] = i;
                return;
            }
            index = firstMax[index];
        }
    }

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

    // 查找最长不重复子串长度
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

    // 3数和
    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> arrayList = new ArrayList<>();
        if (nums == null || nums.length < 3) {
            return arrayList;
        }
        // 数组排序 时间复杂度: O(nLog(n))
        Arrays.sort(nums);
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
                    } else if (sum < 0) { // 小于0，b 数小了，需要增大b
                        b++;
                    } else { // 大于0，c 数大了，需要减小c
                        c--;
                    }
                }
            }
            a++;
        } while (nums[a] <= 0 && a < n - 2);
        return arrayList;
    }

    private static int count(char[] chars, int k, int p1, int p2) {
        if (p2 - p1 + 1 < k) {
            return 0;
        }
        int[] times = new int[26];  //  26个字母
        //  统计出现频次
        for (int i = p1; i <= p2; ++i) {
            ++times[chars[i] - 'a'];
        }
        //  如果该字符出现频次小于k，则不可能出现在结果子串中
        //  分别排除，然后挪动两个指针
        while (p2 - p1 + 1 >= k && times[chars[p1] - 'a'] < k) {
            ++p1;
        }
        while (p2 - p1 + 1 >= k && times[chars[p2] - 'a'] < k) {
            --p2;
        }

        if (p2 - p1 + 1 < k) {
            return 0;
        }
        //  得到临时子串，再递归处理
        for (int i = p1; i <= p2; ++i) {
            //  如果第i个不符合要求，切分成左右两段分别递归求得
            if (times[chars[i] - 'a'] < k) {
                return Math.max(count(chars, k, p1, i - 1), count(chars, k, i + 1, p2));
            }
        }
        return p2 - p1 + 1;
    }

    public static String decodeString(String s) {
        if (!s.contains("[")) {
            return s;
        }
        StringBuilder stringBuilder = new StringBuilder(s);
        Stack<Integer> leftIndex = new Stack<>();
        char left = '[', right = ']';
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == left) {
                leftIndex.push(i);
            }
            if (s.charAt(i) == right) {
                int lefti = leftIndex.pop();
                String str = stringBuilder.substring(lefti + 1, i);
                int numindex = 0;
                for (int j = lefti - 1; j >= 0; j--) {
                    if (j == 0) {
                        numindex = 0;
                        break;
                    }
                    if (!Character.isDigit(s.charAt(j))) {
                        numindex = j;
                        break;
                    }
                }
                numindex = numindex == 0 ? 0 : numindex + 1;
                int number = Integer.parseInt(stringBuilder.substring(numindex, lefti));
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < number; j++) {
                    sb.append(str);
                }
                stringBuilder.replace(numindex, i + 1, sb.toString());
                break;
            }
        }
        return decodeString(stringBuilder.toString());
    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode x = l1, y = l2;
        ListNode result = new ListNode((x.val + y.val) % 10);
        ListNode tempNode = result;
        int i = (x.val + y.val) / 10;
        x = x.next;
        y = y.next;
        while (x != null || y != null) {
            if (y != null && x != null) {
                tempNode.next = new ListNode((x.val + y.val + i) % 10);
                i = (x.val + y.val + i) / 10;
                x = x.next;
                y = y.next;
            } else if (y != null) {
                tempNode.next = new ListNode((y.val + i) % 10);
                i = (y.val + i) / 10;
                y = y.next;
            } else {
                tempNode.next = new ListNode((x.val + i) % 10);
                i = (x.val + i) / 10;
                x = x.next;
            }
            tempNode = tempNode.next;
        }
        if (i != 0) {
            tempNode.next = new ListNode(i);
        }
        return result;
    }

    public static String originalDigits(String s) {
        String[] digit = new String[]{"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
        StringBuilder result = new StringBuilder();
        Map<Character, Integer> charAndNum = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            if (charAndNum.containsKey(s.charAt(i))) {
                charAndNum.put(s.charAt(i), charAndNum.get(s.charAt(i)) + 1);
            } else {
                charAndNum.put(s.charAt(i), 1);
            }
        }
        Map<String, Integer> map = new HashMap<>();
        if (charAndNum.containsKey('z')) {
            Integer z = charAndNum.get('z');
            map.put(digit[0], z);
            Integer e = charAndNum.get('e');
            Integer r = charAndNum.get('r');
            Integer o = charAndNum.get('o');
            charAndNum.put('e', e - z);
            charAndNum.put('r', r - z);
            charAndNum.put('o', o - z);
        }

        if (charAndNum.containsKey('w')) {
            Integer z = charAndNum.get('w');
            map.put(digit[2], z);
            Integer t = charAndNum.get('t');
            Integer o = charAndNum.get('o');
            charAndNum.put('t', t - z);
            charAndNum.put('o', o - z);
        }
        if (charAndNum.containsKey('u')) {
            Integer z = charAndNum.get('u');
            map.put(digit[4], z);
            Integer f = charAndNum.get('f');
            Integer r = charAndNum.get('r');
            Integer o = charAndNum.get('o');
            charAndNum.put('f', f - z);
            charAndNum.put('r', r - z);
            charAndNum.put('o', o - z);
        }
        if (charAndNum.containsKey('x')) {
            Integer z = charAndNum.get('x');
            map.put(digit[6], z);
            Integer s1 = charAndNum.get('s');
            Integer i = charAndNum.get('i');
            charAndNum.put('s', s1 - z);
            charAndNum.put('i', i - z);
        }
        if (charAndNum.containsKey('g')) {
            Integer z = charAndNum.get('g');
            map.put(digit[8], z);
            Integer e = charAndNum.get('e');
            Integer i = charAndNum.get('i');
            Integer h = charAndNum.get('h');
            Integer t = charAndNum.get('t');
            charAndNum.put('e', e - z);
            charAndNum.put('i', i - z);
            charAndNum.put('h', h - z);
            charAndNum.put('t', t - z);
        }

        if (charAndNum.containsKey('t') && charAndNum.get('t') != 0) {
            int three_t = charAndNum.get('t');
            map.put(digit[3], three_t);
        }

        if (charAndNum.containsKey('s') && charAndNum.get('s') != 0) {
            int three_t = charAndNum.get('s');
            map.put(digit[7], three_t);
        }

        if (charAndNum.containsKey('f') && charAndNum.get('f') != 0) {
            int three_t = charAndNum.get('f');
            map.put(digit[5], three_t);
            charAndNum.put('i', charAndNum.get('i') - three_t);
        }

        if (charAndNum.containsKey('o') && charAndNum.get('o') != 0) {
            int three_t = charAndNum.get('o');
            map.put(digit[1], three_t);
        }

        if (charAndNum.containsKey('i') && charAndNum.get('i') != 0) {
            int three_t = charAndNum.get('i');
            map.put(digit[9], three_t);
        }

        for (int i = 0; i < digit.length; i++) {
            if (map.get(digit[i]) != null) {
                for (int j = 0; j < map.get(digit[i]); j++) {
                    result.append(i);
                }
            }
        }
        return result.toString();
    }

    public static int myAtoi(String str) {
        if (str == null || str.trim().isEmpty() || str.replaceAll("-", "").trim().isEmpty()) {
            return 0;
        }
        String temp = str.replaceAll(" ", "");
        if (temp.charAt(0) != '-' && temp.charAt(0) != '+' && !Character.isDigit(temp.charAt(0))) {
            return 0;
        }
        int notDigitIndex = 1;
        if (temp.length() < 2 && !Character.isDigit(temp.charAt(0))) {
            return 0;
        }
        for (int i = 1; i < temp.length(); i++) {
            if (Character.isDigit(temp.charAt(i))) {
                notDigitIndex++;
            } else {
                break;
            }
        }
        String numstr = temp.substring(0, notDigitIndex);
        if (numstr.length() < 2) {
            return 0;
        }
        System.out.println(numstr);
        if (numstr.startsWith("-")) {
            try {
                return Integer.parseInt(numstr);
            } catch (Exception e) {
                return Integer.MIN_VALUE;
            }
        } else {
            try {
                return Integer.parseInt(numstr);
            } catch (Exception e) {
                return Integer.MAX_VALUE;
            }
        }
    }

    public static void reorderList(ListNode head) {
        if (head == null || head.next == null) {
            return;
        }

        ListNode slow = head, fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        ListNode halfAntiNode = reverse(slow.next);
        slow.next = null;
        ListNode headTemp = head;
        ListNode positTemp = null, antiTemp = null;
        while (headTemp != null && halfAntiNode != null) {
            positTemp = headTemp.next;
            headTemp.next = halfAntiNode;
            headTemp = positTemp;
            antiTemp = halfAntiNode.next;
            halfAntiNode.next = headTemp;
            halfAntiNode = antiTemp;
        }
    }

    private static ListNode reverse(ListNode head) {
        ListNode temp = head.next;
        head.next = null;
        ListNode index = head;
        ListNode next = null;
        while (temp != null) {
            next = temp.next;
            temp.next = index;
            index = temp;
            temp = next;
        }
        return index;
    }

    private static ListNode getAntiNode(ListNode head) {
        ListNode copy = new ListNode(head.val);
        ListNode copyTemp = copy;
        ListNode headTemp = head.next;
        while (headTemp != null) {
            copyTemp.next = new ListNode(headTemp.val);
            copyTemp = copyTemp.next;
            headTemp = headTemp.next;
        }
        ListNode temp = copy.next;
        copy.next = null;
        ListNode index = copy;
        ListNode next = null;
        int i = 1;
        while (temp != null) {
            next = temp.next;
            temp.next = index;
            index = temp;
            temp = next;
            i++;
        }
        ListNode number = new ListNode(i);
        number.next = index;
        return number;
    }

    public static int mySqrt(int x) {

        double a = Math.sqrt(x);
        String str = String.valueOf(a);
        System.out.println(str);
        return Integer.parseInt(str.substring(0, str.indexOf(".")));
    }

    public static int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];
        Arrays.fill(dp[0], 1);
        for (int i = 1; i < m; i++) {
            dp[i][0] = 1;
        }
        dp[0][0] = 1;
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i][j - 1] + dp[i - 1][j];
            }
        }
        return dp[m - 1][n - 1];
    }

    private static void printNode(ListNode head) {
        ListNode temp = head;
        while (temp != null) {
            System.out.print(String.format("%d -> ", temp.val));
            temp = temp.next;
        }
    }

    // 两数之和
    public int[] twoSum(int[] nums, int target) {
        int[] temp = new int[2];
        int l = nums.length;
        Map<Integer, Integer> numsMap = new HashMap<>(l << 2);
        for (int i = 0; i < l; i++) {
            if (numsMap.containsKey(nums[i])) {
                temp[0] = i;
                temp[1] = numsMap.get(nums[i]);
                return temp;
            }
            numsMap.put(target - nums[i], i);
        }
        return temp;
    }

    // 查找两个有序数组整合后的中位数
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        /**
         如果两个数组的中位数 mid1 < mid2, 则说明合并后的中位数位于 num1.right + num2之间
         否则合并后的中位数位于 nums2.right + nums1 之间 (right 是相对于 mid 而言的)
         getKth 函数负责找到两个数组合并(假设)后有序的数组中的第 k 个元素, k 从 1 开始计算
         **/
        if (nums1.length == 0 && nums2.length == 0) {
            return 0.0;
        }
        int m = nums1.length, n = nums2.length;
        // l: 合并后数组的左半部分的最后一个数 r: 合并后数组的右半部分的第一个数
        int l = (m + n + 1) / 2;
        int r = (m + n + 2) / 2;
        // 如果 m+n 是奇数 getKth 的返回值是相同的, 是偶数则是合并后数组的中间两个数
        if (l == r) {
            return getKth(nums1, 0, nums2, 0, l);
        }
        return (getKth(nums1, 0, nums2, 0, l) + getKth(nums1, 0, nums2, 0, r)) / 2.0;
    }

    private double getKth(int[] nums1, int st1, int[] nums2, int st2, int k) {
        // 边界情况, 如果 nums1数组已经穷尽了, 则只能返回 nums2 中的第 k 个元素
        if (st1 > nums1.length - 1) {
            return nums2[st2 + k - 1];
        }
        if (st2 > nums2.length - 1) {
            return nums1[st1 + k - 1];
        }
        // 边界情况, k = 1 则返回两个数组中最小的那个
        if (k == 1) {
            return Math.min(nums1[st1], nums2[st2]);
        }
        // 在 nums1 和 nums2 当前范围内找出 mid1 和 mid2 判断舍弃哪半部分
        int mid1 = Integer.MAX_VALUE;
        int mid2 = Integer.MAX_VALUE;
        if (st1 + k / 2 - 1 < nums1.length) {
            mid1 = nums1[st1 + k / 2 - 1];
        }
        if (st2 + k / 2 - 1 < nums2.length) {
            mid2 = nums2[st2 + k / 2 - 1];
        }
        // mid1 < mid2 在 nums1.right 和 nums2 之间搜索, 丢掉 k/2 个数.
        if (mid1 < mid2) {
            return getKth(nums1, st1 + k / 2, nums2, st2, k - k / 2);
        } else {
            return getKth(nums1, st1, nums2, st2 + k / 2, k - k / 2);
        }
    }

    public double findMedianSortedArrays2(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        //处理任何一个nums为空数组的情况
        if (m == 0) {
            if (n % 2 != 0) {
                return 1.0 * nums2[n / 2];
            }
            return (nums2[n / 2] + nums2[n / 2 - 1]) / 2.0;
        }
        if (n == 0) {
            if (m % 2 != 0) {
                return 1.0 * nums1[m / 2];
            }
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
        if (a_begin >= a.length) {
            return b[b_begin + k - 1];
        }
        if (b_begin >= b.length) {
            return a[a_begin + k - 1];
        }
        //k为1时，两数组最小的那个为第一个数
        if (k == 1) {
            return Math.min(a[a_begin], b[b_begin]);
        }

        int mid_a = Integer.MAX_VALUE;
        int mid_b = Integer.MAX_VALUE;
        //mid_a / mid_b 分别表示 a数组、b数组中第 k / 2 个数
        if (a_begin + k / 2 - 1 < a.length) {
            mid_a = a[a_begin + k / 2 - 1];
        }
        if (b_begin + k / 2 - 1 < b.length) {
            mid_b = b[b_begin + k / 2 - 1];
        }
        //如果a数组的第 k / 2 个数小于b数组的第 k / 2 个数，
        // 表示总的第 k 个数位于 a的第k / 2个数的后半段，或者是b的第 k / 2个数的前半段
        //由于范围缩小了 k / 2 个数，此时总的第 k 个数实际上等于新的范围内的第 k - k / 2个数，依次递归
        if (mid_a < mid_b) {
            return find_kth(a, a_begin + k / 2, b, b_begin, k - k / 2);
        }
        //否则相反
        return find_kth(a, a_begin, b, b_begin + k / 2, k - k / 2);
    }

    // 子串中重复K个字符的最长子串长度
    public int longestSubstring(String s, int k) {
        int len = s.length();
        if (len == 0 || k > len) {
            return 0;
        }
        if (k < 2) {
            return len;
        }

        return count(s.toCharArray(), k, 0, len - 1);
    }

    public int rob(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int length = nums.length;
        int[] dp = new int[length];
        //偷窃n家房屋获得的金额最大值
        if (length == 1) {
            return nums[0];
        }
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);
        for (int j = 2; j < length; j++) {
            dp[j] = Math.max(dp[j - 1], dp[j - 2] + nums[j]);
        }
        return dp[length - 1];
    }

    // 计算岛屿数量
    public int numIslands(char[][] grid) {
        int sum = 0;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == '1') {
                    sum++;
                    lj(grid, i, j);
                }
            }
        }
        return sum;
    }

    private void lj(char[][] grid, int i, int j) {
        if (i >= 0 && i < grid.length && j >= 0 && j < grid[i].length) {
            grid[i][j] = '2';
            // 上边
            if (i - 1 >= 0 && grid[i - 1][j] == '1') {
                lj(grid, i - 1, j);
            }
            // 下边
            if (i + 1 < grid.length && grid[i + 1][j] == '1') {
                lj(grid, i + 1, j);
            }
            // 左边
            if (j - 1 >= 0 && grid[i][j - 1] == '1') {
                lj(grid, i, j - 1);
            }
            // 右边
            if (j + 1 < grid[i].length && grid[i][j + 1] == '1') {
                lj(grid, i, j + 1);
            }
        }
    }

    public ArrayList<String> restoreIpAddresses(String s) {
        ArrayList<String> result = new ArrayList<String>();
        int len = s.length();

        for (int i = 1; i < 4 && i < len - 2; i++) {
            for (int j = i + 1; j < i + 4 && j < len - 1; j++) {
                for (int k = j + 1; k < j + 4 && k < len; k++) {
                    if (len - k >= 4)      //判断字符串 是否有剩余
                    {
                        continue;
                    }
                    int a = Integer.parseInt(s.substring(0, i));
                    int b = Integer.parseInt(s.substring(i, j));
                    int c = Integer.parseInt(s.substring(j, k));
                    int d = Integer.parseInt(s.substring(k));

                    if (a > 255 || b > 255 || c > 255 || d > 255) {
                        continue;
                    }
                    String ip = a + "." + b + "." + c + "." + d;
                    if (ip.length() < len + 3) {
                        continue;
                    }
                    result.add(ip);
                }
            }
        }
        return result;
    }

    public String multiply(String num1, String num2) {
        char[] result = new char[num1.length() + num2.length()];
        Arrays.fill(result, '0');

        for (int i = num1.length() - 1; i >= 0; i--) {
            int num1Val = num1.charAt(i) - '0';
            for (int j = num2.length() - 1; j >= 0; j--) {
                int num2Val = num2.charAt(j) - '0';
                int sum = (result[i + j + 1] - '0') + num1Val * num2Val;
                result[i + j + 1] = (char) (sum % 10 + '0');
                result[i + j] += sum / 10;
            }
        }

        for (int i = 0; i < result.length; i++) {
            if (result[i] != '0' || i == result.length - 1) {
                return new String(result, i, result.length - i);
            }
        }
        return "0";
    }

    public List<String> findRepeatedDnaSequences(String s) {
        Set visited = new HashSet(), res = new HashSet();
        for (int i = 0; i + 10 <= s.length(); i++) {
            String tmp = s.substring(i, i + 10);
            if (visited.contains(tmp)) {
                res.add(tmp);
            } else {
                visited.add(tmp);
            }
        }
        return new ArrayList<>(res);
    }

    public List<Integer> findDuplicates(int[] nums) {
        List<Integer> result = new ArrayList<>();
        int length = nums.length;
        for (int i = 0; i < length; i++) {
            if (nums[Math.abs(nums[i]) - 1] < 0) {
                result.add(Math.abs(nums[i]));
            } else {
                nums[Math.abs(nums[i]) - 1] *= -1;
            }
        }
        return result;
    }

    public int maxProfit(int[] prices) {
        int maxCur = 0, maxSoFar = 0;
        for (int i = 1; i < prices.length; i++) {
            maxCur = Math.max(0, maxCur += prices[i] - prices[i - 1]);
            maxSoFar = Math.max(maxCur, maxSoFar);
        }
        return maxSoFar;
    }

    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode first = head, second = null, mid = getMid(head);
        second = mid.next;
        mid.next = null;

        first = sortList(first);
        second = sortList(second);
        return merge(first, second);
    }

    //排序
    ListNode merge(ListNode first, ListNode second) {
        if (first == null) {
            return second;
        }
        if (second == null) {
            return first;
        }

        ListNode res = new ListNode(0);
        ListNode curr = res;//控制新链表顺序的point

        while (first != null && second != null) {
            if (first.val < second.val) {
                curr.next = first;
                curr = curr.next;
                first = first.next;
            } else {
                curr.next = second;
                curr = curr.next;
                second = second.next;
            }
        }

        if (first != null) {
            curr.next = first;
        }
        if (second != null) {
            curr.next = second;
        }

        return res.next;
    }

    //将链表平分为两段，返回第一段末尾   例如：5个点返回2号点，6个点返回3号点
    public ListNode getMid(ListNode head) {
        ListNode slow = head, fast = head.next;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }

    public int minimumTotal(List<List<Integer>> triangle) {
        int row = triangle.size();
        int[] dp = new int[row];
        for (int i = 0; i < row; i++) {
            dp[i] = triangle.get(row - 1).get(i);
        }
        for (int i = row - 2; i >= 0; i--) {
            for (int j = 0; j <= i; j++) {
                dp[j] = Math.min(dp[j], dp[j + 1]) + triangle.get(i).get(j);
            }
        }
        return dp[0];
    }

    public static class ListNode {
        int val;

        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }
}
