package com.zalizniak.redispubsubredisson;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisPubsubRedissonApplicationTests {

    @Autowired
    private RedissonClient redisson;

    @Test
    public void contextLoads() throws InterruptedException {

        RTopic topic = redisson.getTopic("anyTopic");
        CountDownLatch latch = new CountDownLatch(2);
        topic.addListener(String.class, (channel, msg) -> {
            log.info("Listener1: message from anyTopic: " + msg);
            latch.countDown();
        });

        topic.addListener(String.class, (channel, msg) -> {
            log.info("Listener2: message from anyTopic: " + msg);
            latch.countDown();
        });

        // in other thread or JVM

        long clientsReceivedMessage = topic.publish("Hello...");
        log.info("Sent message to: " + clientsReceivedMessage + " consumers.");
        latch.await();
    }

}

