package com.eagle.insight.scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EiSchedulerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EiSchedulerServiceApplication.class, args);

	}

}
