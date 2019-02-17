package com.zalizniak.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.zookeeper.serviceregistry.ZookeeperServiceRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@EnableScheduling
@Configuration
@EnableDiscoveryClient
@SpringBootApplication
public class ZookeeperApplication {

    @Autowired
    private AppFromZooKeeperConfig appFromZooKeeperConfig;

    public static void main(String[] args) {
        SpringApplication.run(ZookeeperApplication.class, args);
    }

    @Autowired
    private ZookeeperServiceRegistry serviceRegistry;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Scheduled(fixedDelay = 5_000)
    public void run() {
        log.info("property: " + appFromZooKeeperConfig.getProperty());
        //List<ServiceInstance> list = discoveryClient.getInstances("DJ-Zookeeper");
        //list.forEach(s -> log.info("Service instance: " + s.toString()));
    }

    /*@Bean
    CommandLineRunner commandLineRunner() {
        return args -> {

        };
    }*/
}

