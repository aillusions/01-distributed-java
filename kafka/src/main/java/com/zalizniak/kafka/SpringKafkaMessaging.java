package com.zalizniak.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Service
public class SpringKafkaMessaging {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private final AtomicLong counter = new AtomicLong();

    @PostConstruct
    public void handleLeaderElectionEvent() {
        for (int i = 0; i < 300_000; i++) {
            kafkaTemplate.send("tutorialspoint", " hello... " + i);
        }
    }

    @KafkaListener(topics = "tutorialspoint", groupId = "dj-kafka-group")
    public void listen(String message) {
        long counterValue = counter.incrementAndGet();
        if (counterValue % 1_000 == 0) {
            log.info("Received messages: " + counterValue);
        }
    }
}
