/**
 * FileName: Gf28
 * Author:   Administrator
 * Date:     2019/5/15 17:13
 * Description: GF(2^8)
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.util;

/**
 * 〈GF(2^8)〉
 *
 * @author Administrator
 * @version 3.2.2
 * @create 2019/5/15 17:13
 */
public class Gf28 {
    public static void main(String[] args) {
        int[] table = new int[256];
        int i;

        table[0] = 1;//g^0
        for (i = 1; i < 255; ++i) {
            //下面是m_table[i] = m_table[i-1] * (x + 1)的简写形式
            table[i] = (table[i - 1] << 1) ^ table[i - 1];
            //最高指数已经到了8，需要模上m(x)
            System.out.print((table[i] & 0x100) + ", ");
            if ((table[i] & 0x100) > 255) {
                table[i] ^= 0x11B;
                //用到了前面说到的乘法技巧
            }
        }
    }
}
