/**
 * FileName: MyFilter
 * Author:   zghuang
 * Date:     2019/3/19 11:35
 * Description: 自定义过滤器
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.fliter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * 〈自定义过滤器〉
 *
 * @author zghuang
 * @create 2019/3/19 11:35
 * @version 3.2.2
 */
@WebFilter(urlPatterns = "/*", filterName = "myfilter")
public class MyFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        long start = System.currentTimeMillis();
        chain.doFilter(request,response);
        System.out.println("Execute cost="+(System.currentTimeMillis()-start));
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
