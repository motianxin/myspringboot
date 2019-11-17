/**
 * FileName: AlarmSolution
 * Author:   Administrator
 * Date:     2019/7/4 9:56
 * Description: 告警解决方案model
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.myfumstest.alarm;

import com.zhigang.myspringboot.utils.strutils.JsonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈告警解决方案model〉
 *
 * @author Administrator
 * @version 3.2.2
 * @create 2019/7/4 9:56
 */
public class AlarmSolution {

    public String title;

    public String descriotion;

    public Map<String, List<String>> causeAndStep;


    public static void main(String[] args) {
        AlarmSolution alarmSolution = new AlarmSolution();

        alarmSolution.title = "通信中断";
        alarmSolution.descriotion = "Agent和fums之间通信异常";

        alarmSolution.causeAndStep = new HashMap<String, List<String>>() {{
            List<String> step = new ArrayList<String>() {{
                add("2.1.1 登录到主机，检测agent进程是否存在：${命令}");
                add("2.1.2 进程不在，启动进程");
            }};
            put("2.1 agent进程关闭", step);
            put("2.2 MQ阻塞或者进程异常", new ArrayList<String>() {{
                add("2.2.1 检测MQ进程是否异常");
                add("2.2.2 进程修复");
            }});
        }};

        System.out.println(JsonUtils.obj2Json(alarmSolution));


    }


}
