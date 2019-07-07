/**
 * FileName: QuartzScheduler
 * Author:   admin
 * Date:     2019/4/25 16:05
 * Description: 注入工厂实例和scheduler
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.system.configuration.quartz;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.io.IOException;
import java.util.Properties;

/**
 * 〈注入工厂实例和scheduler〉
 *
 * @author admin
 * @version 3.2.2
 * @create 2019/4/25 16:05
 */

@Configuration
@Slf4j
public class QuartzScheduler {

    @Autowired
    private QuartzJobFactory quartzJobFactory;


    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }


    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setJobFactory(quartzJobFactory);
        log.info("myJobFactory: [{}]", quartzJobFactory);
        return schedulerFactoryBean;
    }

}
