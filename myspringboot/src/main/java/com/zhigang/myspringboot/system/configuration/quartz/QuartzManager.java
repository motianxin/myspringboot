/**
 * FileName: QuartzManager
 * Author:   admin
 * Date:     2019/4/25 15:54
 * Description: 定时任务管理类
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.system.configuration.quartz;

import com.zhigang.myspringboot.quartz.Myjob;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 〈定时任务管理类〉
 *
 * @author admin
 * @version 3.2.2
 * @create 2019/4/25 15:54
 */
@Configuration
public class QuartzManager {

    public static final String JOB_NAME = "job1";

    public static final String GROUP_NAME = "group1";


    /**
     * 默认15秒执行一次
     */
    public static final String DEFAULT_CRON = "*/15 * * * * ?";

    /**
     * 任务调度
     */
    @Resource
    private Scheduler scheduler;

    /**
     * 开始执行定时任务
     */
    public void startJob() throws SchedulerException {
        // startJobTask(scheduler);
        this.scheduler.start();
    }

    /**
     * 启动定时任务
     *
     * @param scheduler
     */
    private void startJobTask(Scheduler scheduler) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(Myjob.class).withIdentity(QuartzManager.JOB_NAME,
                QuartzManager.GROUP_NAME).storeDurably().build();
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(QuartzManager.DEFAULT_CRON);
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(QuartzManager.JOB_NAME,
                QuartzManager.GROUP_NAME).forJob(jobDetail).withSchedule(cronScheduleBuilder).build();
        scheduler.addJob(jobDetail, true);
        scheduler.scheduleJob(cronTrigger);
    }

    /**
     * 获取Job信息
     *
     * @param name
     * @param group
     */
    public String getjobInfo(String name, String group) throws SchedulerException {
        TriggerKey triggerKey = new TriggerKey(name, group);
        CronTrigger cronTrigger = (CronTrigger) this.scheduler.getTrigger(triggerKey);
        return String.format("time:%s,state:%s", cronTrigger.getCronExpression(),
                this.scheduler.getTriggerState(triggerKey).name());
    }

    /**
     * 修改任务的执行时间
     *
     * @param name
     * @param group
     * @param cron cron表达式
     * @return
     * @throws SchedulerException
     */
    public boolean modifyJob(String name, String group, String cron) throws SchedulerException {
        Date date = null;
        TriggerKey triggerKey = new TriggerKey(name, group);
        CronTrigger cronTrigger = (CronTrigger) this.scheduler.getTrigger(triggerKey);
        String oldTime = cronTrigger.getCronExpression();
        if (!oldTime.equalsIgnoreCase(cron)) {
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
            CronTrigger trigger =
                    TriggerBuilder.newTrigger().withIdentity(name, group).withSchedule(cronScheduleBuilder).build();
            date = this.scheduler.rescheduleJob(triggerKey, trigger);
        }
        return date != null;
    }

    /**
     * 暂停所有任务
     *
     * @throws SchedulerException
     */
    public void pauseAllJob() throws SchedulerException {
        this.scheduler.pauseAll();
    }

    /**
     * 暂停某个任务
     *
     * @param name
     * @param group
     * @throws SchedulerException
     */
    public void pauseJob(String name, String group) throws SchedulerException {
        JobKey jobKey = new JobKey(name, group);
        JobDetail jobDetail = this.scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            return;
        }
        this.scheduler.pauseJob(jobKey);
    }

    /**
     * 恢复所有任务
     *
     * @throws SchedulerException
     */
    public void resumeAllJob() throws SchedulerException {
        this.scheduler.resumeAll();
    }

    /**
     * 恢复某个任务
     */
    public void resumeJob(String name, String group) throws SchedulerException {
        JobKey jobKey = new JobKey(name, group);
        JobDetail jobDetail = this.scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            return;
        }
        this.scheduler.resumeJob(jobKey);
    }

    /**
     * 删除某个任务
     *
     * @param name
     * @param group
     * @throws SchedulerException
     */
    public void deleteJob(String name, String group) throws SchedulerException {
        JobKey jobKey = new JobKey(name, group);
        JobDetail jobDetail = this.scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            return;
        }
        this.scheduler.deleteJob(jobKey);
    }
}
