package com.zalizniak.zookeeperlock;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
@SpringBootApplication
public class ZookeeperLockApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZookeeperLockApplication.class, args);
    }

    @Bean
    @Autowired
    public InterProcessMutex interProcessMutex(CuratorFramework client) {
        return new InterProcessMutex(client, "/siTest/dj.inter-process.lock");
    }

    @Bean
    @Autowired
    CommandLineRunner commandLineRunner(InterProcessMutex lock) {
        return args -> {

            if (lock.acquire(1, TimeUnit.SECONDS)) {
                try {
                    log.info("");
                    log.info("do some work inside of the critical section here..");
                    log.info("");
                } finally {
                    lock.release();
                }
            }
        };
    }
}
