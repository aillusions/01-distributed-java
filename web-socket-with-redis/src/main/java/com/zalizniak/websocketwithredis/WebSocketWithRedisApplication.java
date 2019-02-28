package com.zalizniak.websocketwithredis;

import com.zalizniak.websocketwithredis.config.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties.class)
public class WebSocketWithRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebSocketWithRedisApplication.class, args);
    }
}
