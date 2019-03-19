/**
 * FileName: MyInterceptor
 * Author:   zghuang
 * Date:     2019/3/19 11:41
 * Description: 自定义拦截器
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 〈自定义拦截器〉
 *
 * @author zghuang
 * @create 2019/3/19 11:41
 * @version 3.2.2
 */
@Component
public class MyInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
