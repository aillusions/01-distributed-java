package com.zalizniak.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@Slf4j
@Configuration
public class RedisConfig {

    @Value("${com.zalizniak.dj-redis.redis.host}")
    private String redisHost;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        LettuceConnectionFactory lettuceConFactory = new LettuceConnectionFactory();
        lettuceConFactory.setHostName(redisHost);
        lettuceConFactory.setPort(6379);
        return lettuceConFactory;
    }
}
