/**
 * FileName: DefaultView
 * Author:   admin
 * Date:     2018/12/21 17:02
 * Description: 默认首页
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @program: DefaultView
 * @Description 一句话描述
 * @Author 墨天心
 * @Date 2019/11/16 12:09
 **/
@Configuration
public class DefaultView extends WebMvcConfigurerAdapter {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/index.html");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        super.addViewControllers(registry);
    }
}
