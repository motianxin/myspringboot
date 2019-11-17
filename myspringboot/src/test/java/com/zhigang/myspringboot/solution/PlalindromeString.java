package com.zhigang.myspringboot.solution;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * @program: Code
 * @Description 一句话描述
 * @Author admin
 * @Date 2019/8/2 16:54
 * @Version 3.2.2
 **/
public class PlalindromeString {
    public static boolean canCross(int[] stones) {
        HashMap<Integer, Set<Integer>> map = new HashMap<>();
        for (int i = 0; i < stones.length; i++) {
            map.put(stones[i], new HashSet<Integer>());
        }
        map.get(0).add(0);
        boolean canJump;
        for (int i = 0; i < stones.length - 1; i++) {
            canJump = false;
            for (int k : map.get(stones[i])) {
                for (int step = k - 1; step <= k + 1; step++) {
                    if (step > 0 && map.containsKey(stones[i] + step)) {
                        canJump = true;
                        map.get(stones[i] + step).add(step);
                    }
                }
            }
            if (!canJump) {
                break;
            }
        }
        return map.get(stones[stones.length - 1]).size() > 0;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        String ch = sc.nextLine();
        System.out.println(cul(str, ch));
        sc.close();

    }

    public static int cul(String str) {
        int l = str.length();
        for (int i = l - 1; i >= 0; i--) {
            System.out.println(str.charAt(i));
            if (Character.isSpace(str.charAt(i))) {
                return l - i;
            }
        }
        return l;
    }

    public static int cul(String str, String ch) {
        int sum = 0;
        String temp = str.toLowerCase();
        String chtemp = ch.toLowerCase();
        for (int i = 0; i < str.length(); i++) {
            if (chtemp.charAt(0) == temp.charAt(i)) {
                sum++;
            }
        }
        return sum;
    }

    private static boolean checkIp(String mask) {


        return mask.length() == 0;
    }

    // 判断一个字符串是否回文，算法中用不到了
    @Deprecated
    private boolean isPlalindrome(String s) {
        int len = s.length();
        for (int i = 0; i < len / 2; i++) {
            if (s.charAt(i) != s.charAt(len - 1 - i)) {
                return false;
            }
        }
        return true;
    }

    // 预处理字符串，在两个字符之间加上#
    private String preHandleString(String s) {
        StringBuffer sb = new StringBuffer();
        char[] value = s.toCharArray();
        int len = value.length;
        sb.append('#');
        for (int i = 0; i < len; i++) {
            sb.append(value[i]);
            sb.append('#');
        }
        return sb.toString();
    }

    // 寻找最长回文字串
    public String longestPalindrome(String s) {
        if (s == null) {
            return null;
        }
        if (s.isEmpty()) {
            return "";
        }
        int start = 0;
        int end = 0;
        for (int i = 0; i < s.length(); i++) {
            int len1 = getLength(i, i, s);
            int len2 = getLength(i, i + 1, s);
            int len = Math.max(len1, len2);
            if ((end - start) < len) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
            if (end - start > s.length() - i) {
                break;
            }
        }
        return s.substring(start, end + 1);
    }

    public int getLength(int l, int r, String s) {
        int left = l;
        int right = r;
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return right - left - 1;
    }

    // 寻找最长回文字串
    public String findLongestPlalindromeString(String s) {
        // 先预处理字符串
        String str = preHandleString(s);
        // 处理后的字串长度
        int len = str.length();
        // 右边界
        int rightSide = 0;
        // 右边界对应的回文串中心
        int rightSideCenter = 0;
        // 保存以每个字符为中心的回文长度一半（向下取整）
        int[] halfLenArr = new int[len];
        // 记录回文中心
        int center = 0;
        // 记录最长回文长度
        int longestHalf = 0;
        for (int i = 1; i < len; i += 2) {
            // 是否需要中心扩展
            boolean needCalc = true;
            // 如果在右边界的覆盖之内
            if (rightSide > i) {
                // 计算相对rightSideCenter的对称位置
                int leftCenter = 2 * rightSideCenter - i;
                // 根据回文性质得到的结论
                halfLenArr[i] = halfLenArr[leftCenter];
                // 如果超过了右边界，进行调整
                if (i + halfLenArr[i] > rightSide) {
                    halfLenArr[i] = rightSide - i;
                }
                // 如果根据已知条件计算得出的最长回文小于右边界，则不需要扩展了
                if (i + halfLenArr[leftCenter] < rightSide) {
                    // 直接推出结论
                    needCalc = false;
                }
            }
            // 中心扩展
            if (needCalc) {
                while (i - 1 - halfLenArr[i] >= 0 && i + 1 + halfLenArr[i] < len) {
                    if (str.charAt(i + 1 + halfLenArr[i]) == str.charAt(i - 1 - halfLenArr[i])) {
                        halfLenArr[i]++;
                    } else {
                        break;
                    }
                }
                // 更新右边界及中心
                rightSide = i + halfLenArr[i];
                rightSideCenter = i;
                // 记录最长回文串
                if (halfLenArr[i] > longestHalf) {
                    center = i;
                    longestHalf = halfLenArr[i];
                }
            }
        }
        // 去掉之前添加的#
        StringBuffer sb = new StringBuffer();
        for (int i = center - longestHalf + 1; i <= center + longestHalf; i += 2) {
            sb.append(str.charAt(i));
        }
        //System.out.println(String.format("数组内容为：%s", Arrays.toString(halfLenArr)));
        return sb.toString();
    }

    public int maximalRectangle(char[][] matrix) {
        if (matrix.length == 0) {
            return 0;
        }
        int m = matrix.length;
        int n = matrix[0].length;

        int[] left = new int[n]; // initialize left as the leftmost boundary possible
        int[] right = new int[n];
        int[] height = new int[n];

        Arrays.fill(right, n); // initialize right as the rightmost boundary possible

        int maxarea = 0;
        for (int i = 0; i < m; i++) {
            int cur_left = 0, cur_right = n;
            // update height
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '1') {
                    height[j]++;
                    left[j] = Math.max(left[j], cur_left);
                } else {
                    height[j] = 0;
                    left[j] = 0;
                    cur_left = j + 1;
                }
            }
           /* // update left
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '1') {
                    left[j] = Math.max(left[j], cur_left);
                } else {
                    left[j] = 0;
                    cur_left = j + 1;
                }
            }*/
            // update right
            for (int j = n - 1; j >= 0; j--) {
                if (matrix[i][j] == '1') {
                    right[j] = Math.min(right[j], cur_right);
                } else {
                    right[j] = n;
                    cur_right = j;
                }
            }
            // update area
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '1') {
                    maxarea = Math.max(maxarea, (right[j] - left[j]) * height[j]);
                }
            }
        }
        return maxarea;
    }

    public boolean isScramble(String s1, String s2) {
        if (s1.length() != s2.length()) {
            return false;
        }
        if (s1.equals(s2)) {
            return true;
        }

        //判断两个字符串每个字母出现的次数是否一致
        int[] letters = new int[26];
        for (int i = 0; i < s1.length(); i++) {
            letters[s1.charAt(i) - 'a']++;
            letters[s2.charAt(i) - 'a']--;
        }
        //如果两个字符串的字母出现不一致直接返回 false
        for (int i = 0; i < 26; i++) {
            if (letters[i] != 0) {
                return false;
            }
        }
        //遍历每个切割位置
        for (int i = 1; i < s1.length(); i++) {
            //对应情况 1 ，判断 S1 的子树能否变为 S2 相应部分
            if ((isScramble(s1.substring(0, i), s2.substring(0, i)) && isScramble(s1.substring(i), s2.substring(i))) || (isScramble(s1.substring(i), s2.substring(0, s2.length() - i)) && isScramble(s1.substring(0, i), s2.substring(s2.length() - i)))) {
                return true;
            }
        }
        return false;
    }

    private boolean checkStr(String s1, String s2) {
        if (s1.length() != s2.length()) {
            return false;
        }
        if (s1.equals(s2)) {
            return true;
        }
        //遍历每个切割位置
        for (int i = 1; i < s1.length(); i++) {
            //对应情况 1 ，判断 S1 的子树能否变为 S2 相应部分
            if ((checkStr(s1.substring(0, i), s2.substring(0, i)) && checkStr(s1.substring(i), s2.substring(i))) || (checkStr(s1.substring(i), s2.substring(0, s2.length() - i)) && checkStr(s1.substring(0, i), s2.substring(s2.length() - i)))) {
                return true;
            }
        }
        return false;
    }

    /*
     * 功能: 判断两台计算机IP地址是同一子网络。
     * 输入参数：    String Mask: 子网掩码，格式：“255.255.255.0”；
     *               String ip1: 计算机1的IP地址，格式：“192.168.0.254”；
     *               String ip2: 计算机2的IP地址，格式：“192.168.0.1”；
     *

     * 返回值：      0：IP1与IP2属于同一子网络；     1：IP地址或子网掩码格式非法；    2：IP1与IP2不属于同一子网络
     */
    public int checkNetSegment(String mask, String ip1, String ip2) {
        /*在这里实现功能*/

        boolean n = checkIp(mask);


        return 0;
    }
}
