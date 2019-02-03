package com.zalizniak.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

@Slf4j
@Configuration
public class RedisConfig {

    @Value("${com.zalizniak.dj-redis.redis.host}")
    private String redisHost;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {

        LettuceConnectionFactory jedisConFactory = new LettuceConnectionFactory();
        jedisConFactory.setHostName(redisHost);
        jedisConFactory.setPort(6379);
        return jedisConFactory;
    }

    //@Bean
    //public RedisTemplate<String, String> redisTemplate() {
    //    RedisTemplate<String, String> template = new RedisTemplate<>();
    //    template.setConnectionFactory(redisConnectionFactory());
    //    // template.setEnableTransactionSupport(true);
    //    return template;
    //}

    @Bean
    public StringRedisTemplate stringRedisTemplate() {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(redisConnectionFactory());
        stringRedisTemplate.setEnableTransactionSupport(true);
        return stringRedisTemplate;
    }
}
