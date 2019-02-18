package com.zalizniak.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.zookeeper.ZookeeperProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.Role;
import org.springframework.integration.zookeeper.config.LeaderInitiatorFactoryBean;

import java.util.function.Supplier;

@Slf4j
@Configuration
@EnableAutoConfiguration
public class ZookeeperElectionConfig {

    public static final String COORDINATOR_LEADER_ROLE = "COORDINATOR_LEADER_ROLE";

    @Bean
    public ZookeeperProperties zookeeperProperties() {
        ZookeeperProperties zkp = new ZookeeperProperties();
        zkp.setConnectString("localhost:2181");
        return zkp;
    }

    /**
     * See ZookeeperAutoConfiguration
     * Create: /siTest/COORDINATOR_LEADER_ROLE
     */
    @Bean
    public LeaderInitiatorFactoryBean leaderInitiator(CuratorFramework client) {
        return new LeaderInitiatorFactoryBean()
                .setClient(client)
                .setPath("/siTest/")
                .setRole(COORDINATOR_LEADER_ROLE);
    }

  /*  @Bean
    @InboundChannelAdapter(channel = "stringsChannel", autoStartup = "false", poller = @Poller(fixedDelay = "100"))
    @Role(COORDINATOR_LEADER_ROLE)
    public Supplier<String> inboundChannelAdapter() {
        return () -> COORDINATOR_LEADER_ROLE;
    }*/
  /*  @Slf4j
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
    }*/
}
