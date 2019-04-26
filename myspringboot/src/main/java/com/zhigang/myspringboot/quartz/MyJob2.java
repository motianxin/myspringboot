/**
 * FileName: MyJob2
 * Author:   zghuang
 * Date:     2019/4/26 9:50
 * Description: 测试任务2
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 〈测试任务2〉
 *
 * @author zghuang
 * @create 2019/4/26 9:50
 * @version 3.2.2
 */
@Slf4j
public class MyJob2 implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("it is my secend task.");
    }
}
