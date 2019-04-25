/**
 * FileName: QuartzScheduler
 * Author:   zghuang
 * Date:     2019/4/25 16:05
 * Description: 注入工厂实例和scheduler
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.configuration.quartz;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * 〈注入工厂实例和scheduler〉
 *
 * @author zghuang
 * @version 3.2.2
 * @create 2019/4/25 16:05
 */

@Configuration
public class QuartzScheduler {

    @Autowired
    private QuartzJobFactory quartzJobFactory;

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setJobFactory(quartzJobFactory);
        System.out.println("myJobFactory:" + quartzJobFactory);
        return schedulerFactoryBean;
    }

    @Bean
    public Scheduler scheduler() {
        return schedulerFactoryBean().getScheduler();
    }

}
