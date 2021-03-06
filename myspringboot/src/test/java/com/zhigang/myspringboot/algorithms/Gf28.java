/**
 * FileName: Gf28
 * Author:   Administrator
 * Date:     2019/5/15 17:13
 * Description: GF(2^8)
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.algorithms;

import java.util.Arrays;

/**
 * 〈GF(2^8)〉
 *
 * @author Administrator
 * @version 3.2.2
 * @create 2019/5/15 17:13
 */
public class Gf28 {

    /**
     * S盒
     */
    public static final int[][] sBox = {{0x63, 0x7c, 0x77, 0x7b, 0xf2, 0x6b, 0x6f, 0xc5, 0x30, 0x01, 0x67, 0x2b, 0xfe
            , 0xd7, 0xab, 0x76}, {0xca, 0x82, 0xc9, 0x7d, 0xfa, 0x59, 0x47, 0xf0, 0xad, 0xd4, 0xa2, 0xaf, 0x9c, 0xa4,
            0x72, 0xc0}, {0xb7, 0xfd, 0x93, 0x26, 0x36, 0x3f, 0xf7, 0xcc, 0x34, 0xa5, 0xe5, 0xf1, 0x71, 0xd8, 0x31,
            0x15}, {0x04, 0xc7, 0x23, 0xc3, 0x18, 0x96, 0x05, 0x9a, 0x07, 0x12, 0x80, 0xe2, 0xeb, 0x27, 0xb2, 0x75},
            {0x09, 0x83, 0x2c, 0x1a, 0x1b, 0x6e, 0x5a, 0xa0, 0x52, 0x3b, 0xd6, 0xb3, 0x29, 0xe3, 0x2f, 0x84}, {0x53,
            0xd1, 0x00, 0xed, 0x20, 0xfc, 0xb1, 0x5b, 0x6a, 0xcb, 0xbe, 0x39, 0x4a, 0x4c, 0x58, 0xcf}, {0xd0, 0xef,
            0xaa, 0xfb, 0x43, 0x4d, 0x33, 0x85, 0x45, 0xf9, 0x02, 0x7f, 0x50, 0x3c, 0x9f, 0xa8}, {0x51, 0xa3, 0x40,
            0x8f, 0x92, 0x9d, 0x38, 0xf5, 0xbc, 0xb6, 0xda, 0x21, 0x10, 0xff, 0xf3, 0xd2}, {0xcd, 0x0c, 0x13, 0xec,
            0x5f, 0x97, 0x44, 0x17, 0xc4, 0xa7, 0x7e, 0x3d, 0x64, 0x5d, 0x19, 0x73}, {0x60, 0x81, 0x4f, 0xdc, 0x22,
            0x2a, 0x90, 0x88, 0x46, 0xee, 0xb8, 0x14, 0xde, 0x5e, 0x0b, 0xdb}, {0xe0, 0x32, 0x3a, 0x0a, 0x49, 0x06,
            0x24, 0x5c, 0xc2, 0xd3, 0xac, 0x62, 0x91, 0x95, 0xe4, 0x79}, {0xe7, 0xc8, 0x37, 0x6d, 0x8d, 0xd5, 0x4e,
            0xa9, 0x6c, 0x56, 0xf4, 0xea, 0x65, 0x7a, 0xae, 0x08}, {0xba, 0x78, 0x25, 0x2e, 0x1c, 0xa6, 0xb4, 0xc6,
            0xe8, 0xdd, 0x74, 0x1f, 0x4b, 0xbd, 0x8b, 0x8a}, {0x70, 0x3e, 0xb5, 0x66, 0x48, 0x03, 0xf6, 0x0e, 0x61,
            0x35, 0x57, 0xb9, 0x86, 0xc1, 0x1d, 0x9e}, {0xe1, 0xf8, 0x98, 0x11, 0x69, 0xd9, 0x8e, 0x94, 0x9b, 0x1e,
            0x87, 0xe9, 0xce, 0x55, 0x28, 0xdf}, {0x8c, 0xa1, 0x89, 0x0d, 0xbf, 0xe6, 0x42, 0x68, 0x41, 0x99, 0x2d,
            0x0f, 0xb0, 0x54, 0xbb, 0x16}};

    /**
     * 逆S盒
     */
    public static final int[][] sBox2 = {{0x52, 0x09, 0x6a, 0xd5, 0x30, 0x36, 0xa5, 0x38, 0xbf, 0x40, 0xa3, 0x9e,
            0x81, 0xf3, 0xd7, 0xfb}, {0x7c, 0xe3, 0x39, 0x82, 0x9b, 0x2f, 0xff, 0x87, 0x34, 0x8e, 0x43, 0x44, 0xc4,
            0xde, 0xe9, 0xcb}, {0x54, 0x7b, 0x94, 0x32, 0xa6, 0xc2, 0x23, 0x3d, 0xee, 0x4c, 0x95, 0x0b, 0x42, 0xfa,
            0xc3, 0x4e}, {0x08, 0x2e, 0xa1, 0x66, 0x28, 0xd9, 0x24, 0xb2, 0x76, 0x5b, 0xa2, 0x49, 0x6d, 0x8b, 0xd1,
            0x25}, {0x72, 0xf8, 0xf6, 0x64, 0x86, 0x68, 0x98, 0x16, 0xd4, 0xa4, 0x5c, 0xcc, 0x5d, 0x65, 0xb6, 0x92},
            {0x6c, 0x70, 0x48, 0x50, 0xfd, 0xed, 0xb9, 0xda, 0x5e, 0x15, 0x46, 0x57, 0xa7, 0x8d, 0x9d, 0x84}, {0x90,
            0xd8, 0xab, 0x00, 0x8c, 0xbc, 0xd3, 0x0a, 0xf7, 0xe4, 0x58, 0x05, 0xb8, 0xb3, 0x45, 0x06}, {0xd0, 0x2c,
            0x1e, 0x8f, 0xca, 0x3f, 0x0f, 0x02, 0xc1, 0xaf, 0xbd, 0x03, 0x01, 0x13, 0x8a, 0x6b}, {0x3a, 0x91, 0x11,
            0x41, 0x4f, 0x67, 0xdc, 0xea, 0x97, 0xf2, 0xcf, 0xce, 0xf0, 0xb4, 0xe6, 0x73}, {0x96, 0xac, 0x74, 0x22,
            0xe7, 0xad, 0x35, 0x85, 0xe2, 0xf9, 0x37, 0xe8, 0x1c, 0x75, 0xdf, 0x6e}, {0x47, 0xf1, 0x1a, 0x71, 0x1d,
            0x29, 0xc5, 0x89, 0x6f, 0xb7, 0x62, 0x0e, 0xaa, 0x18, 0xbe, 0x1b}, {0xfc, 0x56, 0x3e, 0x4b, 0xc6, 0xd2,
            0x79, 0x20, 0x9a, 0xdb, 0xc0, 0xfe, 0x78, 0xcd, 0x5a, 0xf4}, {0x1f, 0xdd, 0xa8, 0x33, 0x88, 0x07, 0xc7,
            0x31, 0xb1, 0x12, 0x10, 0x59, 0x27, 0x80, 0xec, 0x5f}, {0x60, 0x51, 0x7f, 0xa9, 0x19, 0xb5, 0x4a, 0x0d,
            0x2d, 0xe5, 0x7a, 0x9f, 0x93, 0xc9, 0x9c, 0xef}, {0xa0, 0xe0, 0x3b, 0x4d, 0xae, 0x2a, 0xf5, 0xb0, 0xc8,
            0xeb, 0xbb, 0x3c, 0x83, 0x53, 0x99, 0x61}, {0x17, 0x2b, 0x04, 0x7e, 0xba, 0x77, 0xd6, 0x26, 0xe1, 0x69,
            0x14, 0x63, 0x55, 0x21, 0x0c, 0x7d}};


    public static void test() {
        int c = 0, r = 0;
        Arrays.stream(sBox).forEach(a -> System.out.println(Arrays.toString(a)));
        System.out.println("===============================");
        int s = sBox[r][c];
        System.out.println(s + " = " + Integer.toBinaryString(s) + " = " + ((s & 0xf0) >> 4) + " + " + (s & 0xf));
        System.out.println("===============================");
        Arrays.stream(sBox2).forEach(a -> System.out.println(Arrays.toString(a)));
        System.out.println("===============================");
        int as = sBox2[(s & 0xf0) >> 4][s & 0xf];
        System.out.println(as + " = " + Integer.toBinaryString(as) + " = " + ((as & 0xf0) >> 4) + " + " + (as & 0xf));
    }

    public static int[][] one2TwoArray(int[] table) {
        int length = table.length;
        int sqirt = (int) Math.round(Math.sqrt(length));
        int[][] tmp = new int[sqirt][sqirt];
        for (int i = 0; i < length; i++) {
            tmp[i / sqirt][i % sqirt] = table[i];
        }
        return tmp;
    }

    public static int getGf251(int x) {
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
            int t1 = a1 - q * b1, t2 = a2 - q * b2, t3 = a3 - q * b3;
            a1 = b1;
            a2 = b2;
            a3 = b3;
            b1 = t1;
            b2 = t2;
            b3 = t3;
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
    public static long getMaxp(long a, long b) {
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
     * @Description: 两个8位数在GF(2 ^ 8)域中相乘 0<=a,b<=255
     * @Param: [a, b]
     * @return: int
     * @Author: admin
     * @Date: 2019/6/12 22:31
     */
    public static int aMultiplyB(int a, int b) {
        if (a > 255 || b > 255) {
            return 0;
        }
        int result = 0;
        int[] array = getGfByint(a);
        for (int i = 0; i < array.length; i++) {
            result ^= (((b >> i) & 1) * array[i]);
        }
        return result;
    }

    /**
     * @Description: 得到一个数右移八位的数组
     * @Param: [a]
     * @return: int[]
     * @Author: admin
     * @Date: 2019/6/12 23:06
     */
    public static int[] getGfByint(int a) {
        int[] array = new int[8];
        for (int i = 0; i < 8; i++) {
            array[i] = a << i;
            if (array[i] > 255) {
                array[i] = (array[i] & 0xFF) ^ 0x1B;
            }
        }
        return array;
    }

    public static void creatGf28() {
        // 利用生成元g = x + 1 , initTable[n] = g^n (g^0 = 1) 生成正表
        // 知道n 得到g^n 的值
        int[] initTable = new int[256];
        initTable[0] = 1;
        for (int i = 1; i < 255; ++i) {
            //initTable[i] = initTable[i-1] * (x + 1)的简写形式，因为g = 0x3 = 11
            initTable[i] = (initTable[i - 1] << 1) ^ initTable[i - 1];
            //最高指数已经到了8，需要模上m(x) = x^8 + x^4 + x^3 + x + 1  (0x11b)
            if ((initTable[i] & 0x100) > 255) {
                initTable[i] ^= 0x11B;
            }
        }
        System.out.println("初始化数组值，得到正表：");
        printArray(initTable);
        // 其中周期性质：g^(i+j) = 1, (i+j)%255=1
        // 得到反表：知道g^n 求 n：
        int[] arcTable = new int[256];
        for (int i = 0; i < 255; i++) {
            arcTable[initTable[i]] = i;
        }
        System.out.println("由正表得到反表：");
        printArray(arcTable);
        // 利用周期性得到g^a的逆元g^b, b = 255 - a; midTable[g^i] = g^(255-i)
        int[] midTable = new int[256];
        for (int i = 1; i < 256; i++) {
            // k = g^a
            int k = arcTable[i];
            k = 255 - k;
            k = k % 255;
            midTable[i] = initTable[k];
        }
        System.out.println("根据正反表得到S盒中间盒：");
        printArray(midTable);
        System.out.println("S中间盒做仿射变换得到S盒：");

        int[][] sbox = new int[16][16];
        for (int i = 0; i < midTable.length; i++) {
            sbox[i / 16][i % 16] = byteTransformation(midTable[i], 0x63);
            System.out.print(Integer.toHexString(byteTransformation(midTable[i], 0x63)) + ", ");
            if ((i + 1) % 16 == 0) {
                System.out.println();
            }
        }
        System.out.println("通过S盒得到逆S盒：");

        int[][] insSbox = new int[16][16];
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                insSbox[sbox[i][j] >> 4][sbox[i][j] & 0x0f] = (i << 4) ^ j;
            }
        }

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                System.out.print(Integer.toHexString(insSbox[i][j]) + ", ");
            }
            System.out.println();
        }

    }

    private static void printArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(Integer.toHexString(array[i]) + ", ");
            if ((i + 1) % 16 == 0) {
                System.out.println();
            }
        }
    }

    /**
     * @Author Administrator
     * @Description S盒的逆元做仿射变换得到S盒
     * 仿射变换公式：b[i] = b[i] ^ b[((i+4)%8)] ^ b[((i+5)%8)] ^ b[((i+6)%8)] ^ b[((i+7)%8)] ^ (((0x63 >> i) & 1)
     * @Param [a, x]
     * @Return int
     * @date 2019/6/15 01:49
     **/
    public static int byteTransformation(int a, int x) {
        int[] tmp = new int[8];

        for (int i = 0; i < 8; i++) {
            tmp[i] =
                    (((a >> i) & 0x1) ^ ((a >> ((i + 4) % 8)) & 0x1) ^ ((a >> ((i + 5) % 8)) & 0x1) ^ ((a >> ((i + 6) % 8)) & 0x1) ^ ((a >> ((i + 7) % 8)) & 0x1) ^ ((x >> i) & 0x1)) << i;
        }
        return Arrays.stream(tmp).sum();
    }

    public static void main(String[] args) {
        int g = 0x3, a = 1, temp = 1;
        for (int i = 1; i < 267; i++) {
            System.out.print(temp + ", ");
            temp = aMultiplyB(g, temp);
        }
        System.out.println(aMultiplyB(5, 82));
        int x1 = aMultiplyB(3, 199);
        System.out.println(x1);
        int x2 = aMultiplyB(x1, 3);
        System.out.println(x2);
        int x = aMultiplyB(x2, 3);
        System.out.println(x);
        System.out.println(Integer.toBinaryString(109));
        creatGf28();
    }

}
