package com.zalizniak.redis.lock;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RedisLockWorker2 {

    @Async
    public Future lockable(RLock lock, List<String> logCollector) throws InterruptedException {
        Thread.sleep(500);

        logCollector.add("RedisLockWorker2 started.");

        logCollector.add("RedisLockWorker2 check: is locked: " + lock.isLocked());

        lock.lock(4000, TimeUnit.MILLISECONDS);
        logCollector.add("RedisLockWorker2 retrieved lock");

        Thread.sleep(1);
        lock.unlock();
        logCollector.add("RedisLockWorker2 unlocked.");

        return CompletableFuture.completedFuture(true);
    }
}
