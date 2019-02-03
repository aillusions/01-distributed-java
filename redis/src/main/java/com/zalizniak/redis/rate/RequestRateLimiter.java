package com.zalizniak.redis.rate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RequestRateLimiter {

    private static final String REDIS_RATE_LIMITER_KEY = "dj-redis-rate-lim-";
    public static final int REDIS_RATE_LIMITER_DURATION_SEC = 1;
    private static final long REDIS_RATE_LIMITER_TIMEOUT = 31 * 60 * 1000; // 10 min
    public static final int REDIS_RATE_LIMITER_BATCH_SIZE = 5;

    @Autowired
    private RateLimiterService rateLimiterService;

    public void acquire(String key) {

        rateLimiterService.acquire(
                REDIS_RATE_LIMITER_KEY + key,
                REDIS_RATE_LIMITER_DURATION_SEC,
                REDIS_RATE_LIMITER_BATCH_SIZE,
                1,
                REDIS_RATE_LIMITER_TIMEOUT);
    }

}
