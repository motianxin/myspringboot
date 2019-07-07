/**
 * FileName: QuartzJobFactory
 * Author:   admin
 * Date:     2019/4/25 16:02
 * Description: 定义工厂类创建任务实例
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.system.configuration.quartz;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Component;

/**
 * 〈定义工厂类创建任务实例〉
 *
 * @author admin
 * @version 3.2.2
 * @create 2019/4/25 16:02
 */
@Component
public class QuartzJobFactory extends AdaptableJobFactory {

    @Autowired
    private AutowireCapableBeanFactory capableBeanFactory;

    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        // 调用父类的方法
        Object jobInstance = super.createJobInstance(bundle);
        // 进行注入
        capableBeanFactory.autowireBean(jobInstance);
        return jobInstance;
    }
}
