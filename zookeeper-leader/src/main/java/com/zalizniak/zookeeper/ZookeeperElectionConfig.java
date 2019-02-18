package com.zalizniak.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.Role;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.leader.Context;
import org.springframework.integration.leader.event.OnGrantedEvent;
import org.springframework.integration.leader.event.OnRevokedEvent;
import org.springframework.integration.zookeeper.config.LeaderInitiatorFactoryBean;
import org.springframework.messaging.PollableChannel;

import java.util.concurrent.CountDownLatch;
import java.util.function.Supplier;

@Slf4j
@Configuration
@EnableAutoConfiguration
public class ZookeeperElectionConfig {

    public static final String COORDINATOR_LEADER_ROLE = "COORDINATOR_LEADER_ROLE";

    @Autowired
    private CountDownLatch leaderElectedLatch;

    /**
     * See: ZookeeperAutoConfiguration
     * See: /siTest/COORDINATOR_LEADER_ROLE
     */
    @Bean
    public LeaderInitiatorFactoryBean leaderInitiator(CuratorFramework client, ApplicationEventPublisher eventPublisher) {

        LeaderInitiatorFactoryBean rv = new LeaderInitiatorFactoryBean()
                .setClient(client)
                .setPath("/siTest/")
                .setRole(COORDINATOR_LEADER_ROLE);

        rv.setApplicationEventPublisher(eventPublisher);

        return rv;
    }

    @Bean
    @InboundChannelAdapter(channel = "stringsChannel", autoStartup = "false", poller = @Poller(fixedDelay = "100"))
    @Role(COORDINATOR_LEADER_ROLE)
    public Supplier<String> inboundChannelAdapter() {
        return () -> COORDINATOR_LEADER_ROLE;
    }

    @Bean
    public PollableChannel stringsChannel() {
        return new QueueChannel();
    }

    @EventListener(OnGrantedEvent.class)
    public synchronized void start(OnGrantedEvent evt) {
        Context ctx = evt.getContext();
        log.info("####################################");
        log.info("#");
        log.info("#  Leader election: onGranted for role: " + ctx.getRole());
        log.info("#");
        log.info("####################################");
        leaderElectedLatch.countDown();
    }

    @EventListener(OnRevokedEvent.class)
    public synchronized void stop(OnRevokedEvent evt) {
        Context ctx = evt.getContext();
        log.info("Leader election: onRevoked for role: " + ctx.getRole());
    }
}
