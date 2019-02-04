package com.zalizniak.redis.lock;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RedisLockWorker2 {

    @Autowired
    private RedissonClient redisson;

    @Async
    public void lockable(String uuid, List<String> logCollector) throws InterruptedException {
        Thread.sleep(500);

        logCollector.add("RedisLockWorker2 started.");

        RLock lock = redisson.getLock(uuid);

        logCollector.add("RedisLockWorker2 check: is locked: " + lock.isLocked());

        lock.lock(4000, TimeUnit.MILLISECONDS);
        logCollector.add("RedisLockWorker2 retrieved lock");

        Thread.sleep(1);
        lock.unlock();
        logCollector.add("RedisLockWorker2 unlocked.");

        Thread.sleep(100);
    }
}
