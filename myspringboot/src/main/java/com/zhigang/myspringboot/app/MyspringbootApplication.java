package com.zhigang.myspringboot.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.zhigang.myspringboot"})
@EntityScan(basePackages = {"com.zhigang.myspringboot"})
public class MyspringbootApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(MyspringbootApplication.class);

	public static void main(String[] args) {
		LOGGER.info("---------MyspringbootApplication start----------");
		SpringApplication.run(MyspringbootApplication.class, args);
	}

}

