package com.zalizniak.redis.pubsub;

import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisPubsubTest extends TestCase {

    @Autowired
    private CountDownLatch latch1;

    @Autowired
    private CountDownLatch latch2;

    @Autowired
    private RedisMessageListener1 redisMessageListener1;

    @Autowired
    private RedisMessageListener2 redisMessageListener2;

    @Test
    public void test() throws InterruptedException {

        latch1.await();
        latch2.await();

        long size1 = redisMessageListener1.getMessages().size();
        long size2 = redisMessageListener2.getMessages().size();

        log.info("size1: " + size1 + " size2: " + size2);

        assertTrue(size1 > 0);
        assertTrue(size2 > 0);

        assertEquals(size1, size2);
    }
}
