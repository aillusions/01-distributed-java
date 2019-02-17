package com.zalizniak.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@EnableScheduling
@Configuration
@EnableDiscoveryClient
@SpringBootApplication
@EnableConfigurationProperties(AppFromZooKeeperProps.class)
public class ZookeeperApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZookeeperApplication.class, args);
    }

    @Autowired
    private AppFromZooKeeperConfig appFromZooKeeperConfig;

    @Autowired
    private AppFromZooKeeperProps appFromZooKeeperProps;

    @Scheduled(fixedDelay = 1_000)
    public void run() {
        log.info("conf: " + appFromZooKeeperConfig.getProperty());
        log.info("props: " + appFromZooKeeperProps.getProperty());
    }
}

