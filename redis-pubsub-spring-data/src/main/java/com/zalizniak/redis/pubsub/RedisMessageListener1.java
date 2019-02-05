package com.zalizniak.redis.pubsub;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class RedisMessageListener1 implements MessageListener {

    @Autowired
    private CountDownLatch latch1;

    @Getter
    private final List<String> messages = Collections.synchronizedList(new LinkedList<>());

    @Override
    public void onMessage(final Message message, final byte[] pattern) {
        messages.add(message.toString());
        log.info("Message received: " + message.toString() + " in thread: " + Thread.currentThread().getName());
        latch1.countDown();
    }
}