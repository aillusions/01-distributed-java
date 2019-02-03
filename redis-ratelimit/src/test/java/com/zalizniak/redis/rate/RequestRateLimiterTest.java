package com.zalizniak.redis.rate;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RequestRateLimiterTest {

    @Autowired
    private RequestRateLimiter requestRateLimiter;

    @Test
    public void shouldShowThrottle() throws InterruptedException {
        for (int i = 0; i < 20; i++) {
            long start = System.currentTimeMillis();
            requestRateLimiter.acquire("second-test");
            long delta = System.currentTimeMillis() - start;
            log.info("requestRateLimiter acquired in: " + delta + " ms.");
        }
    }

    @Test
    public void shouldThrottle() {
        int emailsNum = 0;
        long startOverall = System.currentTimeMillis();
        for (int i = 0; i <= (RequestRateLimiter.REDIS_RATE_LIMITER_BATCH_SIZE * 4); i++) {
            long start = System.currentTimeMillis();
            requestRateLimiter.acquire("first-test");
            log.info("Acquired in: " + (System.currentTimeMillis() - start) + " ms.");
            emailsNum++;
        }

        long avgMsPerEmail = (RequestRateLimiter.REDIS_RATE_LIMITER_DURATION_SEC * 1000) / RequestRateLimiter.REDIS_RATE_LIMITER_BATCH_SIZE;

        long expectedMs = (avgMsPerEmail * emailsNum) - 1000;

        long spentMs = System.currentTimeMillis() - startOverall;
        long emailsPerSec = (emailsNum - RequestRateLimiter.REDIS_RATE_LIMITER_BATCH_SIZE) / (spentMs / 1000);

        log.info("Sent: " + emailsNum + " emails, in: " + spentMs + " ms, with avg: " + emailsPerSec + " emails/sec.");

        assertTrue(spentMs >= expectedMs);

        assertTrue(14 >= emailsPerSec);
    }
}
