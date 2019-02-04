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
import java.util.concurrent.*;

/**
 * CountDownLatch
 * CyclicBarrier
 * Semaphore
 * Mutex
 */
@Slf4j
@RunWith(SpringRunner.class)
@EnableAsync
@SpringBootTest(classes = {
        RedisLockTest.Worker1.class,
        RedisLockTest.Worker2.class,
        RedissonSpringDataConfig.class})
public class RedisLockTest extends TestCase {

    @Autowired
    private Worker1 lockWorker1;

    @Autowired
    private Worker2 lockWorker2;

    @Autowired
    private RedissonClient redisson;

    @Test
    public void testLock() throws InterruptedException, ExecutionException, BrokenBarrierException {

        String key = java.util.UUID.randomUUID().toString();

        List<String> logCollector = Collections.synchronizedList(new LinkedList<>());

        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        //Semaphore semaphore = new Semaphore(1, true);

        RLock lock = redisson.getLock(key);

        long start = System.currentTimeMillis();

        Future future1 = lockWorker1.lockable(lock, logCollector, cyclicBarrier);
        Future future2 = lockWorker2.lockable(lock, logCollector, cyclicBarrier);

        future1.get();
        future2.get();

        String expected = "[Worker1 acquired lock., Worker2 check: is locked: true, Worker1 unlocked., Worker2 acquired lock, Worker2 unlocked.]";
        log.info("logCollector: in " + (System.currentTimeMillis() - start) + " ms: " + logCollector.toString());

        assertEquals(expected, logCollector.toString());
    }

    @Slf4j
    @Service
    public static class Worker1 {

        @Async
        public Future lockable(RLock lock,
                               List<String> logCollector,
                               CyclicBarrier cyclicBarrier) throws InterruptedException, BrokenBarrierException {

            lock.lock(5000, TimeUnit.MILLISECONDS);
            logCollector.add("Worker1 acquired lock.");

            cyclicBarrier.await(); //
            cyclicBarrier.reset(); // give Worker1 chance to check isLocked
            cyclicBarrier.await(); //

            lock.unlock();
            logCollector.add("Worker1 unlocked.");

            return CompletableFuture.completedFuture(true);
        }
    }

    @Slf4j
    @Service
    public static class Worker2 {

        @Async
        public Future lockable(RLock lock,
                               List<String> logCollector,
                               CyclicBarrier cyclicBarrier) throws InterruptedException, BrokenBarrierException {

            cyclicBarrier.await(); // Give Worker1 chance to lock

            logCollector.add("Worker2 check: is locked: " + lock.isLocked());

            cyclicBarrier.await(); // Give Worker1 chance to unlock

            lock.lock(5000, TimeUnit.MILLISECONDS);
            logCollector.add("Worker2 acquired lock");

            lock.unlock();
            logCollector.add("Worker2 unlocked.");

            return CompletableFuture.completedFuture(true);
        }
    }

}
