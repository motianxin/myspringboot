/**
 * FileName: WebConfig
 * Author:   zghuang
 * Date:     2019/3/19 14:50
 * Description: 配置类
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.app;

import com.zhigang.myspringboot.system.interceptor.MyInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Arrays;

/**
 * 〈配置类〉
 *
 * @author zghuang
 * @create 2019/3/19 14:50
 * @version 3.2.2
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyInterceptor()).addPathPatterns(Arrays.asList("/**"));
        super.addInterceptors(registry);
    }

}
