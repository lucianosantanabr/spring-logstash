package com.spring.logstash;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
public class LogstashApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogstashApplication.class, args);
	}

}
