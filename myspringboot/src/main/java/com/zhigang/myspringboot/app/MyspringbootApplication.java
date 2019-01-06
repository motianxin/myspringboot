package com.zhigang.myspringboot.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.zhigang.myspringboot"})
@EntityScan(basePackages = {"com.zhigang.myspringboot"})
public class MyspringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyspringbootApplication.class, args);
	}

}

