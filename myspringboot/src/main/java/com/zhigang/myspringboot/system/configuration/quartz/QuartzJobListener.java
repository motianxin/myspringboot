/**
 * FileName: QuartzJobListener
 * Author:   admin
 * Date:     2019/4/25 15:56
 * Description: 定时任务启动监听类
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.system.configuration.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import javax.annotation.Resource;

/**
 * 〈定时任务启动监听类〉
 *
 * @author admin
 * @version 3.2.2
 * @create 2019/4/25 15:56
 */
@Configuration
@Slf4j
public class QuartzJobListener implements ApplicationListener<ContextRefreshedEvent> {

    @Resource
    private QuartzManager quartzManager;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        try {
            this.quartzManager.startJob();
            QuartzJobListener.log.info("任务已经启动......");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始注入scheduler
     */
    @Bean
    public Scheduler scheduler() throws SchedulerException {
        SchedulerFactory schedulerFactoryBean = new StdSchedulerFactory();
        return schedulerFactoryBean.getScheduler();
    }
}
