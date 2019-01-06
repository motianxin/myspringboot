/**
 * FileName: DefaultView
 * Author:   zghuang
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
 * 〈一句话功能简述〉<br> 
 * 〈默认首页〉
 *
 * @author zghuang
 * @create 2018/12/21 17:02
 * @version 3.2.2
 */
@Configuration
public class DefaultView extends WebMvcConfigurerAdapter {
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("forward:/index.html");
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
		super.addViewControllers(registry);
	}
}
