/**
 * FileName: StringTools
 * Author:   admin
 * Date:     2019/1/10 20:24
 * Description: 字符串工具类
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.utils.strutils;

import com.zhigang.myspringboot.utils.common.Constans;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 〈一句话功能简述〉<br>
 * 〈字符串工具类〉
 *
 * @author admin
 * @version 3.2.2
 * @create 2019/1/10 20:24
 */
public class StringTools {


    public static String hexToChinese(String str) {
        String value = str;
        if (str.contains(Constans.ARRAY_SEPARATOR)) {
            try {
                String[] temps = str.split(Constans.ARRAY_SEPARATOR);
                byte[] bs = new byte[temps.length];
                for (int j = 0; j < temps.length; j++) {
                    //转换byte[]
                    bs[j] = (byte) Integer.parseInt(temps[j], 16);
                }
                value = new String(bs, "UTF-8");
            } catch (Exception e) {
                value = str;
            }
        }
        return value;
    }

    public static String dateFormat(String pattern, Date date) {
        return new SimpleDateFormat(pattern).format(date);
    }


    public static String formatNullStr(String str) {
        return str == null ? "" : str;
    }

    public static byte[] toByteArray(String hexString) {

        hexString = hexString.toLowerCase();
        final byte[] byteArray = new byte[hexString.length() / 2];
        int k = 0;
        for (int i = 0; i < byteArray.length; i++) {
            //因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
            byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
            byteArray[i] = (byte) (high << 4 | low);
            k += 2;
        }
        return byteArray;
    }

    public static String toHexString(byte[] byteArray) {
        if (byteArray == null || byteArray.length < 1) {
            throw new IllegalArgumentException("this byteArray must not be null or empty");
        }

        final StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < byteArray.length; i++) {
            if ((byteArray[i] & 0xff) < 0x10)//0~F前面不零
            {
                hexString.append("0");
            }
            hexString.append(Integer.toHexString(0xFF & byteArray[i]));
        }
        return hexString.toString().toLowerCase();
    }
}
