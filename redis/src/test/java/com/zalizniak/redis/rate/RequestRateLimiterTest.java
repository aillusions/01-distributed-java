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
    public void shouldThrottle() {
        int emailsNum = 0;
        long startOverall = System.currentTimeMillis();
        for (int i = 0; i <= (requestRateLimiter.getBatchSize() * 4); i++) {
            long start = System.currentTimeMillis();
            requestRateLimiter.acquire();
            log.info("Acquired in: " + (System.currentTimeMillis() - start) + " ms.");
            emailsNum++;
        }

        long EMAIL_RATE_LIMITER_AVG_MS_PER_EMAIL = (RequestRateLimiter.REDIS_RATE_LIMITER_DURATION_SEC * 1000) / requestRateLimiter.getBatchSize();

        long expectedMs = (EMAIL_RATE_LIMITER_AVG_MS_PER_EMAIL * emailsNum) - 1000;

        long spentMs = System.currentTimeMillis() - startOverall;
        long emailsPerSec = (emailsNum - requestRateLimiter.getBatchSize()) / (spentMs / 1000);

        log.info("Sent: " + emailsNum + " emails, in: " + spentMs + " ms, with avg: " + emailsPerSec + " emails/sec.");

        assertTrue(spentMs >= expectedMs);

        assertTrue(14 >= emailsPerSec);
    }
}
