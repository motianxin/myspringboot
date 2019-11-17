package com.zhigang.myspringboot.system.configuration.httpsserver;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: Code
 * @Description 一句话描述
 * @Author admin
 * @Date 2019/6/22 11:56
 * @Version 3.2.2
 **/

@Configuration
public class HttpsConfig {


    @Value("${server.port}")
    private int sPort;

    @Value("${http.port}")
    private int hPort;

    @Bean
    @ConditionalOnProperty(name = "condition.http2https", havingValue = "true", matchIfMissing = false)
    public Connector connector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        connector.setPort(this.hPort);
        connector.setSecure(false);
        connector.setRedirectPort(this.sPort);
        return connector;
    }

    @Bean
    @ConditionalOnProperty(name = "condition.http2https", havingValue = "true", matchIfMissing = false)
    public TomcatServletWebServerFactory tomcatServletWebServerFactory() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint securityConstraint = new SecurityConstraint();
                securityConstraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint);
            }
        };
        tomcat.addAdditionalTomcatConnectors(connector());
        return tomcat;
    }

}
