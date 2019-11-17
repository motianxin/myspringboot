package com.zhigang.myspringboot.myfumstest.alarm; /**
 * FileName: com.zhigang.myspringboot.myfumstest.alarm.RunTimeTest
 * Author:   Administrator
 * Date:     2019/5/29 11:24
 * Description: text
 * History:
 * <author>          <time>          <version>          <desc>
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 〈text〉
 *
 * @author Administrator
 * @version 3.2.2
 * @create 2019/5/29 11:24
 */
public class RunTimeTest {

    private static final String[] CHECK_PROCESS_CMD = new String[]{"sh", "-c", "ps -ef | grep [i]notify.sh"};
    // String CHECK_PROCESS_CMD = "sh -c \"ps -ef | grep [i]notify.sh\"";

    public static void main(String[] args) {
        BufferedReader br = null;
        String ls = null;
        StringBuffer sb = new StringBuffer();
        try {

            Process process = Runtime.getRuntime().exec(CHECK_PROCESS_CMD);
            Thread.sleep(500);
            System.out.println(process.exitValue());
            if (process.exitValue() == 0) {
                br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            }
            while ((ls = br.readLine()) != null) {
                sb.append(ls);
            }
            System.out.println(sb.toString());
            br.close();

            process.getOutputStream().close();
            process.destroy();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
