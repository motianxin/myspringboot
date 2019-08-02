package com.zhigang.myspringboot.solution;

import java.util.Arrays;

/**
 * @program: Code
 * @Description 一句话描述
 * @Author admin
 * @Date 2019/8/2 16:54
 * @Version 3.2.2
 **/
public class PlalindromeString {
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
        System.out.println(String.format("数组内容为：%s", Arrays.toString(halfLenArr)));
        return sb.toString();
    }

    public static void main(String[] args) {

        PlalindromeString ps = new PlalindromeString();

        String[] testStrArr = new String[]{
                "abcdcef",
                "adaelele",
                "cabadabae",
                "aaaabcdefgfedcbaa",
                "aaba",
                "aaaaaaaaa"
        };

        for (String str : testStrArr) {
            System.out.println(String.format("原字串 : %s", str));
            System.out.println(String.format("最长回文串 : %s", ps.findLongestPlalindromeString(str)));
            System.out.println();
        }

    }
}
