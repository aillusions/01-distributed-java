package com.zalizniak.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.integration.leader.Candidate;
import org.springframework.integration.leader.event.DefaultLeaderEventPublisher;
import org.springframework.integration.leader.event.OnGrantedEvent;
import org.springframework.integration.leader.event.OnRevokedEvent;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.integration.support.leader.LockRegistryLeaderInitiator;
import org.springframework.integration.support.locks.LockRegistry;

@Slf4j
@Configuration
public class ElectionConfig {

    private static final String ELECTION_REDIS_KEY_NAME = "dj-redis-node-election-key";
    private static final int ELECTION_REDIS_KEY_EXPIRATION_MS = 60 * 1000; // 1 minute

    @Autowired
    public RedisConnectionFactory redisConnectionFactory;

    @Bean
    public RedisLockRegistry redisLockRegistry() {
        return new RedisLockRegistry(redisConnectionFactory, ELECTION_REDIS_KEY_NAME, ELECTION_REDIS_KEY_EXPIRATION_MS);
    }

    @Bean
    public LockRegistryLeaderInitiator leaderInitiator(LockRegistry locks,
                                                       Candidate candidate,
                                                       ApplicationEventPublisher applicationEventPublisher) {

        LockRegistryLeaderInitiator initiator = new LockRegistryLeaderInitiator(locks, candidate);
        initiator.setLeaderEventPublisher(new DefaultLeaderEventPublisher(applicationEventPublisher));

        return initiator;
    }

    @EventListener(OnGrantedEvent.class)
    public void start() {
        log.info("OnGrantedEvent: start.");
    }

    @EventListener(OnRevokedEvent.class)
    public void stop() {
        log.info("OnRevokedEvent: stop.");
    }

}
