package com.zalizniak.redis.leader;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.integration.leader.Candidate;
import org.springframework.integration.leader.Context;
import org.springframework.integration.leader.DefaultCandidate;
import org.springframework.integration.leader.event.DefaultLeaderEventPublisher;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.integration.support.leader.LockRegistryLeaderInitiator;
import org.springframework.integration.support.locks.LockRegistry;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Configuration
public class RedisElectionConfig {

    public static final String COORDINATOR_LEADER_ROLE = "COORDINATOR_LEADER_ROLE";
    public static final String COORDINATOR_LEADER_REDIS_KEY = "coordinator-leader-redis-key";

    private static final int ELECTION_REDIS_KEY_EXPIRATION_MS = 2 * 1000; // 2 sec

    @Autowired
    public RedisConnectionFactory redisConnectionFactory;

    @Bean
    public RedisLockRegistry coordinationLeaderRedisLockRegistry() {
        return new RedisLockRegistry(redisConnectionFactory, COORDINATOR_LEADER_REDIS_KEY, ELECTION_REDIS_KEY_EXPIRATION_MS);
    }

    @Bean
    public LockRegistryLeaderInitiator leaderInitiator(LockRegistry coordinationLeaderRedisLockRegistry,
                                                       Candidate coordinatorLeaderCandidate,
                                                       ApplicationEventPublisher applicationEventPublisher) {

        LockRegistryLeaderInitiator initiator = new LockRegistryLeaderInitiator(coordinationLeaderRedisLockRegistry, coordinatorLeaderCandidate);
        initiator.setLeaderEventPublisher(new DefaultLeaderEventPublisher(applicationEventPublisher));

        return initiator;
    }

    @Slf4j
    @Component("coordinatorLeaderCandidate")
    public static class CoordinatorLeaderCandidate extends DefaultCandidate {

        public CoordinatorLeaderCandidate() {
            super(UUID.randomUUID().toString(), COORDINATOR_LEADER_ROLE);
        }

        @Getter
        private volatile boolean leader;

        @Autowired
        private ApplicationEventPublisher eventPublisher;

        @Override
        public void onGranted(Context ctx) {
            super.onGranted(ctx);
            leader = true;
            log.info("Leader election: onGranted for role: " + ctx.getRole());
            eventPublisher.publishEvent(new LeaderElectionEvent(this, leader));
        }

        @Override
        public void onRevoked(Context ctx) {
            super.onRevoked(ctx);
            leader = false;
            eventPublisher.publishEvent(new LeaderElectionEvent(this, leader));
            log.info("Leader election: onRevoked for role: " + ctx.getRole());
        }
    }
}
