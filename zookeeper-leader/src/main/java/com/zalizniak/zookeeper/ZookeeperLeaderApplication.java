package com.zalizniak.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.CountDownLatch;

@Slf4j
@EnableScheduling
@Configuration
@SpringBootApplication

public class ZookeeperLeaderApplication {

    @Bean
    public CountDownLatch leaderElectedLatch() {
        return new CountDownLatch(1);
    }

    @Bean
    @Autowired
    CommandLineRunner commandLineRunner(CountDownLatch leaderElectedLatch) {
        return args -> {
            leaderElectedLatch.await();
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(ZookeeperLeaderApplication.class, args);
    }
}

