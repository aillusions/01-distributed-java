package com.zalizniak.redis;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Slf4j
public class RedisMessageListener2 implements MessageListener {

    @Getter
    private final List<String> messages = Collections.synchronizedList(new LinkedList<>());

    @Override
    public void onMessage(final Message message, final byte[] pattern) {
        messages.add(message.toString());
        log.info("Message received: " + message.toString());
    }
}