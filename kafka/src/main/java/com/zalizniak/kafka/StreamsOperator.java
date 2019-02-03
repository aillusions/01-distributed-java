package com.zalizniak.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Service
public class StreamsOperator {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private final AtomicLong counter = new AtomicLong();

    // @Scheduled(fixedDelay = 1000)
    // public void runBySchedule() {
    //     kafkaTemplate.send("streams-json-input", " {\"key\":\"somekey\",\"words\":[\"word" + counter.incrementAndGet() + "\"]}");
    // }

    @KafkaListener(topics = "streams-json-output", groupId = "dj-kafka-group")
    public void listen(String message) {
        log.info("Received message: " + message);
    }
}
