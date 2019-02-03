package com.zalizniak.redis.lock;

import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisLockTest extends TestCase {

    @Autowired
    private RedisLockWorker1 redisLockWorker1;

    @Autowired
    private RedisLockWorker2 redisLockWorker2;

    @Test
    public void testLock() throws InterruptedException {

        String uuid = java.util.UUID.randomUUID().toString();

        List<String> logCollector = Collections.synchronizedList(new LinkedList<>());

        redisLockWorker1.lockable(uuid, logCollector);
        redisLockWorker2.lockable(uuid, logCollector);

        Thread.sleep(4000);

        String expected = "[RedisLockWorker1 started., RedisLockWorker1 retrieved lock., RedisLockWorker2 started., RedisLockWorker2 check: is locked: true, RedisLockWorker1 unlocked., RedisLockWorker2 retrieved lock, RedisLockWorker2 unlocked.]";
        log.info("logCollector: " + logCollector.toString());

        assertEquals(expected, logCollector.toString());
    }
}
