package com.zalizniak.redis;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.RedissonNodeConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Distributed lock
 */

@Slf4j
@Component
public class DistLockService {

    @Value("${com.zalizniak.dj-redis.redis.host}")
    private String redisHost;

    @Getter
    private RedissonClient redisson;

    @PostConstruct
    public void initIt() {
        Config config = new RedissonNodeConfig();

        config.useSingleServer()
                .setAddress("redis://" + redisHost + ":6379")
                .setConnectionMinimumIdleSize(1)
                .setConnectionPoolSize(2);

        redisson = Redisson.create(config);
    }

}
