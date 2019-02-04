package com.zalizniak.redis.lock;

import com.zalizniak.redis.RedissonSpringDataConfig;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Slf4j
@RunWith(SpringRunner.class)
@EnableAsync
@SpringBootTest(classes = {
        RedisLockTest.RedisLockWorker1.class,
        RedisLockTest.RedisLockWorker2.class,
        RedissonSpringDataConfig.class})
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

        future1.get();
        future2.get();

        String expected = "[RedisLockWorker1 started., RedisLockWorker1 acquired lock., RedisLockWorker2 started., RedisLockWorker2 check: is locked: true, RedisLockWorker1 unlocked., RedisLockWorker2 acquired lock, RedisLockWorker2 unlocked.]";
        log.info("logCollector: " + logCollector.toString());

        assertEquals(expected, logCollector.toString());
    }

    @Slf4j
    @Service
    public static class RedisLockWorker1 {

        @Async
        public Future lockable(RLock lock, List<String> logCollector) throws InterruptedException {

            logCollector.add("RedisLockWorker1 started.");

            lock.lock(5000, TimeUnit.MILLISECONDS);
            logCollector.add("RedisLockWorker1 acquired lock.");

            Thread.sleep(1000); // give Worker2 chance to check isLocked
            lock.unlock();
            logCollector.add("RedisLockWorker1 unlocked.");

            return CompletableFuture.completedFuture(true);
        }
    }

    @Slf4j
    @Service
    public static class RedisLockWorker2 {

        @Async
        public Future lockable(RLock lock, List<String> logCollector) throws InterruptedException {
            Thread.sleep(500); // Give Worker1 chance to acquire lock

            logCollector.add("RedisLockWorker2 started.");

            logCollector.add("RedisLockWorker2 check: is locked: " + lock.isLocked());

            lock.lock(5000, TimeUnit.MILLISECONDS);
            logCollector.add("RedisLockWorker2 acquired lock");

            lock.unlock();
            logCollector.add("RedisLockWorker2 unlocked.");

            return CompletableFuture.completedFuture(true);
        }
    }

}
