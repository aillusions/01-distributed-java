package com.zalizniak.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@EnableScheduling
@Configuration
@EnableDiscoveryClient
@SpringBootApplication

public class ZookeeperApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZookeeperApplication.class, args);
    }
}

