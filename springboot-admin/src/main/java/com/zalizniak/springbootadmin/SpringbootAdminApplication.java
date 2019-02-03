package com.zalizniak.springbootadmin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Slf4j
@EnableWebMvc
@EnableAsync
@EnableScheduling
@SpringBootApplication
public class SpringbootAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootAdminApplication.class, args);
	}

}

