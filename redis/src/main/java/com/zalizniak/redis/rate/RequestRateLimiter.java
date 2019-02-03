package com.zalizniak.redis.rate;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RequestRateLimiter {

    private static final String REDIS_RATE_LIMITER_KEY = "pt-web-ses-send-rate-rate-lim";
    public static final int REDIS_RATE_LIMITER_DURATION_SEC = 1;
    private static final long REDIS_RATE_LIMITER_TIMEOUT = 31 * 60 * 1000; // 10 min

    @Autowired
    private RateLimiterService rateLimiterService;

    @Getter
    private Integer batchSize = 5;

    public void acquire() {

        rateLimiterService.acquire(
                REDIS_RATE_LIMITER_KEY,
                REDIS_RATE_LIMITER_DURATION_SEC,
                batchSize,
                1,
                REDIS_RATE_LIMITER_TIMEOUT);
    }

}
