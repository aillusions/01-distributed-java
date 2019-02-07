package com.zalizniak.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.net.URL;

@Slf4j
@Configuration
public class RedisConfig {

    @Value("${com.zalizniak.dj-redis.redis.host}")
    private String redisHost;


    // @Bean
    // public JedisConnectionFactory redisConnectionFactory() {
    //     RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(redisHost, 6379);
    //     return new JedisConnectionFactory(config);
    // }

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        // LettuceConnectionFactory jedisConFactory = new LettuceConnectionFactory();
        // jedisConFactory.setHostName(redisHost);
        // jedisConFactory.setPort(6379);
        // return jedisConFactory;
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration(redisHost, 6379));
    }
}
