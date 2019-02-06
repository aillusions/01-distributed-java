package com.zalizniak.redis;

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
import java.util.concurrent.*;

/**
 * CountDownLatch
 * CyclicBarrier
 * Semaphore - limits number of
 * Mutex
 */
@Slf4j
@RunWith(SpringRunner.class)
@EnableAsync
@SpringBootTest(classes = {
        RedisRedissonLockApplicationTests.Worker1.class,
        RedissonSpringDataConfig.class})
public class RedisRedissonLockApplicationTests extends TestCase {

    @Autowired
    private Worker1 lockWorker1;

    @Autowired
    private RedissonClient redisson;

    @Test
    public void testLock() throws InterruptedException, ExecutionException, BrokenBarrierException {

        String key = java.util.UUID.randomUUID().toString();
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);

        List<String> logCollector = Collections.synchronizedList(new LinkedList<>());

        RLock lock = redisson.getLock(key);

        long start = System.currentTimeMillis();

        Future future1 = lockWorker1.lockable(lock, logCollector, "1", cyclicBarrier);
        if (cyclicBarrier.await() == 0) {
            cyclicBarrier.reset();
        }
        Future future2 = lockWorker1.lockable(lock, logCollector, "2", cyclicBarrier);
        if (cyclicBarrier.await() == 0) {
            cyclicBarrier.reset();
        }

        future2.get();
        future1.get();

        long timeToRun = (System.currentTimeMillis() - start);

        String expected = "[" +
                "Worker1 is locked: false, " +
                "Worker2 is locked: false, " +
                "Worker2 locked, " +
                "Worker2 is locked: true, " +
                "Worker2 unlocked, " +
                "Worker2 is locked: false, " +
                "Worker1 locked, " +
                "Worker1 is locked: true, " +
                "Worker1 unlocked, " +
                "Worker1 is locked: false" +
                "]";
        log.info("logCollector: in " + timeToRun + " ms: " + logCollector.toString());

        assertEquals(expected, logCollector.toString());
        assertTrue(timeToRun < 300);
    }

    @Slf4j
    @Service
    public static class Worker1 {

        @Async
        public Future lockable(RLock lock,
                               List<String> logCollector,
                               String idx,
                               CyclicBarrier cyclicBarrier) throws BrokenBarrierException, InterruptedException {

            logCollector.add("Worker" + idx + " is locked: " + lock.isLocked());
            if (cyclicBarrier.await() == 0) {
                cyclicBarrier.reset();
            }

            lock.lock(5000, TimeUnit.MILLISECONDS);
            logCollector.add("Worker" + idx + " locked");

            logCollector.add("Worker" + idx + " is locked: " + lock.isLocked());

            lock.unlock();
            logCollector.add("Worker" + idx + " unlocked");

            logCollector.add("Worker" + idx + " is locked: " + lock.isLocked());

            return CompletableFuture.completedFuture(true);
        }
    }
}
