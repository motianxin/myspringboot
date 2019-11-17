package com.zhigang.myspringboot.app;

import com.zhigang.myspringboot.system.interceptor.MyInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Arrays;

/**
 * @program: WebConfig
 * @Description 一句话描述
 * @Author 墨天心
 * @Date 2019/11/16 11:59
 **/
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    /**
     * @Description: 添加拦截器
     * @Param: [registry]
     * @return: void
     * @Author: 墨天心
     * @Date: 2019/11/9 20:27
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyInterceptor()).addPathPatterns(Arrays.asList("/**"));
        super.addInterceptors(registry);
    }
}
