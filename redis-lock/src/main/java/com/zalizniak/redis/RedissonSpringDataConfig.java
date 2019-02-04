package com.zalizniak.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.RedissonNodeConfig;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;


@Configuration
public class RedissonSpringDataConfig {

    @Value("${com.zalizniak.dj-redis.redis.host}")
    private String redisHost;

    @Bean
    public RedissonConnectionFactory redissonConnectionFactory(RedissonClient redisson) {
        return new RedissonConnectionFactory(redisson);
    }

    // @Bean(destroyMethod = "shutdown")
    // public RedissonClient redisson(@Value("classpath:/redisson.yaml") Resource configFile) throws IOException {
    //     Config config = Config.fromYAML(configFile.getInputStream());
    //     return Redisson.create(config);
    // }

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson() throws IOException {
        Config config = new RedissonNodeConfig();

        config.useSingleServer()
                .setAddress("redis://" + redisHost + ":6379")
                .setConnectionMinimumIdleSize(1)
                .setConnectionPoolSize(2);
        return Redisson.create(config);
    }
}
