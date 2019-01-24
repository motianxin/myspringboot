/**
 * FileName: LambdaTest
 * Author:   zghuang
 * Date:     2019/1/22 19:23
 * Description: lambda test
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.lambda;

import java.io.File;

/**
 * 〈一句话功能简述〉<br> 
 * 〈lambda test〉
 *
 * @author zghuang
 * @create 2019/1/22 19:23
 * @version 3.2.2
 */
public class LambdaTest {

	public static void main(String[] args) {
		File[] hiddenFiles = new File(".").listFiles(File::isHidden);
	}



}
