package com.zhigang.myspringboot.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
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

}