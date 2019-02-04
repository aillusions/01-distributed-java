package com.zalizniak.redis.lock;

import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisLockTest extends TestCase {

    @Autowired
    private RedisLockWorker1 redisLockWorker1;

    @Autowired
    private RedisLockWorker2 redisLockWorker2;

    @Autowired
    private RedissonClient redisson;

    @Test
    public void testLock() throws InterruptedException, ExecutionException {

        String key = java.util.UUID.randomUUID().toString();

        List<String> logCollector = Collections.synchronizedList(new LinkedList<>());

        //Semaphore semaphore = new Semaphore(1, true);
        RLock lock1 = redisson.getLock(key);
        RLock lock2 = redisson.getLock(key);

        Future future1 = redisLockWorker1.lockable(lock1, logCollector);
        Future future2 = redisLockWorker2.lockable(lock2, logCollector);

        try {
            future1.get();
            future2.get();
        } catch (Exception e) {
            log.info("Error: " + logCollector, e);
        }

        String expected = "[RedisLockWorker1 started., RedisLockWorker1 retrieved lock., RedisLockWorker2 started., RedisLockWorker2 check: is locked: true, RedisLockWorker1 unlocked., RedisLockWorker2 retrieved lock, RedisLockWorker2 unlocked.]";
        log.info("logCollector: " + logCollector.toString());

        assertEquals(expected, logCollector.toString());
    }
}
