package com.zhigang.myspringboot.app;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.zhigang.myspringboot"})
@EnableJpaRepositories(basePackages = {"com.zhigang.myspringboot.repository"})
@EntityScan(basePackages = {"com.zhigang.myspringboot.domain"})
@ServletComponentScan(basePackages = {"com.zhigang.myspringboot.fliter"})
@Slf4j
public class MyspringbootApplication {

	public static void main(String[] args) {
		log.info("---------MyspringbootApplication start----------");
		SpringApplication.run(MyspringbootApplication.class, args);
	}

	@Bean
	public Connector connector(){
		Connector connector=new Connector("org.apache.coyote.http11.Http11NioProtocol");
		connector.setScheme("http");
		connector.setPort(80);
		connector.setSecure(false);
		connector.setRedirectPort(443);
		return connector;
	}

	@Bean
	public TomcatServletWebServerFactory tomcatServletWebServerFactory(Connector connector){
		TomcatServletWebServerFactory tomcat=new TomcatServletWebServerFactory(){
			@Override
			protected void postProcessContext(Context context) {
				SecurityConstraint securityConstraint=new SecurityConstraint();
				securityConstraint.setUserConstraint("CONFIDENTIAL");
				SecurityCollection collection=new SecurityCollection();
				collection.addPattern("/*");
				securityConstraint.addCollection(collection);
				context.addConstraint(securityConstraint);
			}
		};
		tomcat.addAdditionalTomcatConnectors(connector);
		return tomcat;
	}
}