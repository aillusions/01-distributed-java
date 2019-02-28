package com.zalizniak.websocketwithredis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;
import com.zalizniak.websocketwithredis.config.ApplicationProperties;

@SpringBootApplication
@EnableAsync(proxyTargetClass = true)
@EnableConfigurationProperties(ApplicationProperties.class)
public class WebSocketWithRedisApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebSocketWithRedisApplication.class, args);
	}

	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
