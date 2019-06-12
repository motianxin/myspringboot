/**
 * FileName: Gf28
 * Author:   Administrator
 * Date:     2019/5/15 17:13
 * Description: GF(2^8)
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.util;

import java.util.Arrays;

/**
 * 〈GF(2^8)〉
 *
 * @author Administrator
 * @version 3.2.2
 * @create 2019/5/15 17:13
 */
public class Gf28 {
    public static void test() {
/*        int[] table = new int[256];
        table[0] = 1;
        for (int i = 1; i < 255; ++i) {
            //下面是m_table[i] = m_table[i-1] * (x + 1)的简写形式
            table[i] = (table[i - 1] << 1) ^ table[i - 1];
            //最高指数已经到了8，需要模上m(x)
            if ((table[i] & 0x100) > 255) {
                table[i] ^= 0x11B;
                //用到了前面说到的乘法技巧
            }
            // System.out.print(Integer.toBinaryString(table[i]) + ", ");
        }
        System.out.println();
        System.out.println(Arrays.toString(table));
        int[][] array = one2TwoArray(table);
        Arrays.stream(array).forEach(a -> System.out.println(Arrays.toString(a)));

        int[] arc_table = new int[256];
        for (int j = 0; j < 255; j++) {
            arc_table[table[j]] = j;
        }
        System.out.println(Arrays.toString(arc_table));
        Arrays.stream(one2TwoArray(arc_table)).forEach(a -> System.out.println(Arrays.toString(a)));

        int[] inverse_table = new int[256];

        for (int i = 1; i < 256; ++i) {
            int k = arc_table[i];
            k = 255 - k;
            k %= 255;//m_table的取值范围为 [0, 254]
            inverse_table[i] = table[k];

        }
        System.out.println(Arrays.toString(inverse_table));
        Arrays.stream(one2TwoArray(inverse_table)).forEach(a -> System.out.println(Arrays.toString(a)));*/
        int[] gf251 = new int[251];
        gf251[1] = 1;
        for (int i = 2; i < 251; i++) {
            gf251[i] = getGf251(i);
        }
        System.out.println(Arrays.toString(gf251));

        System.out.println(getMaxp(4, 7));

        // System.out.println("result = " + a);
        // System.out.println("a * b % 251 = " + a + " * " + b + " % 251 = " + a * b % 251);

    }

    private static int[][] one2TwoArray(int[] table) {
        int length = table.length;
        int sqirt = (int) Math.round(Math.sqrt(length));
        int[][] tmp = new int[sqirt][sqirt];
        for (int i = 0; i < length; i++) {
            tmp[i / sqirt][i % sqirt] = table[i];
        }
        return tmp;
    }

    private static int getGf251(int x) {
        int gf251 = 251;
        int a1 = 1, a2 = 0, a3 = 251;
        int b1 = 0, b2 = 1, b3 = x;
        if (x == 0) {
            return 0;
        }
        while (true) {
            if (b3 == 1) {
                break;
            }
            int q = a3 / b3;
            // System.out.println("q = " + q);
            int t1 = a1 - q * b1, t2 = a2 - q * b2, t3 = a3 - q * b3;
            /*System.out.println("a1 = " + a1);
            System.out.println("a2 = " + a2);
            System.out.println("a3 = " + a3);
            System.out.println("b1 = " + b1);
            System.out.println("b2 = " + b2);
            System.out.println("b3 = " + b3);*/
            a1 = b1;
            a2 = b2;
            a3 = b3;
            b1 = t1;
            b2 = t2;
            b3 = t3;
            /*System.out.println("a1 = " + a1);
            System.out.println("a2 = " + a2);
            System.out.println("a3 = " + a3);
            System.out.println("b1 = " + b1);
            System.out.println("b2 = " + b2);
            System.out.println("b3 = " + b3);*/
        }
        return (b2 + gf251) % gf251;
    }

    /**
     * @Author Administrator
     * @Description 计算最大公约数
     * @Param [a, b]
     * @Return long
     * @date 2019/6/12 17:19
     **/
    private static long getMaxp(long a, long b) {
        long max = 1;
        long x, y, p = 1;
        if (a < b) {
            x = a;
            y = b;
        } else if (a > b) {
            x = b;
            y = a;
        } else {
            max = a;
            return max;
        }
        while (p != 0) {
            p = y % x;
            y = x;
            max = x;
            x = p;
        }
        return max;
    }

    /**
     * @Description: 两个8位数在GF(2^8)域中相乘 0<=a,b<=255
     * @Param: [a, b]
     * @return: int
     * @Author: zghuang
     * @Date: 2019/6/12 22:31
     */
    private static int aMultiplyB(int a, int b){
        if (a > 255 || b > 255) {
            return 0;
        }
        int result = 0;
        int[] array = getGfByint(a);
        for (int i = 0; i < array.length; i++) {
            // System.out.println("array[" + i + "] = "+ Integer.toBinaryString(array[i]));
            // System.out.println("b = " + Integer.toBinaryString(b >> i));
            result ^= (((b >> i) & 1) * array[i]);
            // System.out.println("result = " + Integer.toBinaryString(result));
        }
        return result;
    }

    /** 
     * @Description: 得到一个数右移八位的数组
     * @Param: [a] 
     * @return: int[] 
     * @Author: zghuang
     * @Date: 2019/6/12 23:06
     */
    private static int[] getGfByint(int a) {
        int[] array = new int[8];
        for (int i = 0; i < 8; i++) {
            array[i] = a << i;
            if (array[i] > 255) {
                array[i] = array[i] & 0xFF ^ 0x1B;
            }
        }
        return array;
    }

    public static void main(String[] args) {
        // System.out.println(aMultiplyB(34, 98));

        int a = 0x11b, b = 0x7;
        System.out.println(Integer.toBinaryString(a / b));
        System.out.println(Integer.toBinaryString(a % b));
        int i = 0;
        int m = aMultiplyB(0x3, 0x3);
        System.out.println(Integer.toBinaryString(m) + " = " +m);
        while (i <= 255) {
            m = aMultiplyB(0x3, m);
            System.out.println(Integer.toBinaryString(m) + " = " +m);
            i++;
        }

    }

}
