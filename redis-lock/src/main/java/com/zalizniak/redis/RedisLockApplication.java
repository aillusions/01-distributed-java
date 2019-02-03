package com.zalizniak.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@SpringBootApplication
public class RedisLockApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisLockApplication.class, args);
	}

}

