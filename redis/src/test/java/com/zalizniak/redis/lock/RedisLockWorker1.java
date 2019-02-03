package com.zalizniak.redis.lock;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RedisLockWorker1 {

    @Autowired
    private DistLockService distLockService;

    @Async
    public void lockable(String uuid, List<String> logCollector) throws InterruptedException {

        Thread.sleep(1);
        logCollector.add("RedisLockWorker1 started.");

        RLock lock = distLockService.getRedisson().getLock(uuid);
        lock.lock(4000, TimeUnit.MILLISECONDS);
        logCollector.add("RedisLockWorker1 retrieved lock.");

        Thread.sleep(1000);
        lock.unlock();
        logCollector.add("RedisLockWorker1 unlocked.");
    }
}
