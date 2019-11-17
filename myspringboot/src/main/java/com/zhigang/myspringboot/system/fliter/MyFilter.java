/**
 * FileName: MyFilter
 * Author:   admin
 * Date:     2019/3/19 11:35
 * Description: 自定义过滤器
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.system.fliter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import java.io.IOException;

/**
 * 〈自定义过滤器〉
 *
 * @author admin
 * @version 3.2.2
 * @create 2019/3/19 11:35
 */
@WebFilter(urlPatterns = "/*", filterName = "myfilter")
public class MyFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
