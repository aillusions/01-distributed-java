package com.zalizniak.redis.ratelimit.redisson;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisRatelimitRedissonApplicationTests {

    @Autowired
    private RedissonClient redisson;

    @Test
    public void contextLoads() {
        RRateLimiter rateLimiter = redisson.getRateLimiter("myRateLimiter");

        // Initialization.
        // Total rate = 10 permits per 1 second.
        rateLimiter.trySetRate(RateType.OVERALL, 10, 1, RateIntervalUnit.SECONDS);

        // acquire 4 permits
        rateLimiter.tryAcquire(4);

        // acquire 4 permits or wait 2 seconds for availability
        rateLimiter.tryAcquire(4, 2, TimeUnit.SECONDS);

        rateLimiter.tryAcquireAsync(2, 2, TimeUnit.SECONDS);

        // acquire 1 permit or wait forever for availability
        rateLimiter.acquire();

        // acquire 1 permit or wait forever for availability
        RFuture<Void> future = rateLimiter.acquireAsync();
    }

}

