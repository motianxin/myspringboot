/**
 * FileName: DruidConfig
 * Author:   zghuang
 * Date:     2019/4/26 11:17
 * Description: DruidConfig
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.system.configuration.druid;

import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 〈DruidConfig〉
 *
 * @author zghuang
 * @create 2019/4/26 11:17
 * @version 3.2.2
 */
@Configuration
public class DruidConfig {

    @Bean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/");
        servletRegistrationBean.addInitParameter("allow","127.0.0.1");
        servletRegistrationBean.addInitParameter("deny","192.168.0.107");
        servletRegistrationBean.addInitParameter("loginUsername","druid");
        servletRegistrationBean.addInitParameter("loginPassword","druid123!");
        servletRegistrationBean.addInitParameter("resetEnable","false");
        return servletRegistrationBean;
    }
}
