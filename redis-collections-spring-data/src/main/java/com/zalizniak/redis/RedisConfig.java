package com.zalizniak.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

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
    public <T> RedisTemplate<String, T> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, T> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        // LettuceConnectionFactory jedisConFactory = new LettuceConnectionFactory();
        // jedisConFactory.setHostName(redisHost);
        // jedisConFactory.setPort(6379);
        // return jedisConFactory;
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration(redisHost, 6379));
    }
}
