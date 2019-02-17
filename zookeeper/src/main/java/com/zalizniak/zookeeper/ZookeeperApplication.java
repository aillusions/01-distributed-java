package com.zalizniak.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.endpoint.event.RefreshEvent;
import org.springframework.cloud.zookeeper.serviceregistry.ZookeeperServiceRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Slf4j
@EnableScheduling
@Configuration
@EnableDiscoveryClient
@SpringBootApplication
public class ZookeeperApplication {

    // Create: /configuration/DJ-Zookeeper/custom-dj-config-property
    @Value("${custom-dj-config-property}")
    private String property;

    public static void main(String[] args) {
        SpringApplication.run(ZookeeperApplication.class, args);
    }

    @Autowired
    private ZookeeperServiceRegistry serviceRegistry;

    @Scheduled(fixedDelay = 5_000)
    public void run() {
        log.info("property: " + property);

        List<ServiceInstance> list = discoveryClient.getInstances("DJ-Zookeeper");
        list.forEach(s -> log.info("Service instance: " + s.toString()));
    }

    /*@Bean
    CommandLineRunner commandLineRunner() {
        return args -> {

        };
    }*/

    @Autowired
    private DiscoveryClient discoveryClient;

    @EventListener(RefreshEvent.class)
    public void onRefreshEvent(RefreshEvent evt) {
        log.info("RefreshEvent: " + evt);
    }
}

