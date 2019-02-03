package com.zalizniak.redis.rate;

import es.moki.ratelimitj.core.limiter.request.RequestLimitRule;
import es.moki.ratelimitj.core.limiter.request.RequestRateLimiter;
import es.moki.ratelimitj.redis.request.RedisRateLimiterFactory;
import io.lettuce.core.RedisClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.Collections;
import java.util.Set;

@Slf4j
@Component
public class RateLimiterService {

    @Value("${com.zalizniak.dj-redis.redis.host}")
    private String redisHost;

    private RedisRateLimiterFactory factory;

    @PostConstruct
    public void initIt() {
        factory = new RedisRateLimiterFactory(RedisClient.create("redis://" + redisHost));
    }

    public boolean overLimit(String keyName, int durationSec, int limit, int consume) {
        Set<RequestLimitRule> rules = Collections.singleton(RequestLimitRule.of(Duration.ofSeconds(durationSec), limit));
        RequestRateLimiter requestRateLimiter = factory.getInstance(rules);

        boolean overLimit = requestRateLimiter.overLimitWhenIncremented(keyName, consume);
        log.debug("overLimit: " + overLimit);

        return overLimit;
    }

    public void acquire(String keyName, int durationSec, int limit, int consume, Long timeoutMs) {
        long start = System.currentTimeMillis();

        boolean overLimit;
        do {
            overLimit = overLimit(keyName, durationSec, limit, consume);
            if (overLimit) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException("RateLimiterService: InterruptedException.", e);
                }

                if (timeoutMs != null) {
                    long spentMs = System.currentTimeMillis() - start;
                    if (spentMs >= timeoutMs) {
                        throw new RuntimeException("RateLimiterService: acquire timeout after " + spentMs + " ms.");
                    }
                }

            }

        } while (overLimit);
    }
}
