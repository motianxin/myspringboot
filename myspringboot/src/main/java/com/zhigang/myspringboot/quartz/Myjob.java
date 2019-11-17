/**
 * FileName: Myjob
 * Author:   admin
 * Date:     2019/4/25 16:14
 * Description: 自定义定时任务
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 〈自定义定时任务〉
 *
 * @author admin
 * @version 3.2.2
 * @create 2019/4/25 16:14
 */
@Slf4j
public class Myjob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Myjob.log.info("The job start execute.");
    }
}
