/**
 * FileName: StringTools
 * Author:   zghuang
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
 * @author zghuang
 * @version 3.2.2
 * @create 2019/1/10 20:24
 */
public class StringTools {


	public static String hexToChinese(String str) {
		String value = str;
		if (str.indexOf(Constans.ARRAY_SEPARATOR) != -1) {
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

	public static String dateFormat(String pattern, Date date){
		return new SimpleDateFormat(pattern).format(date);
	}


}
