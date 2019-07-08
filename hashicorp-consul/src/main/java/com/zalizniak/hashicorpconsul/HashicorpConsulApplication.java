package com.zalizniak.hashicorpconsul;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@SpringBootApplication
@EnableDiscoveryClient
public class HashicorpConsulApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(HashicorpConsulApplication.class, args);
    }

    /*@Autowired
    private DiscoveryClient discoveryClient;

    public Optional<URI> serviceUrl() {
        return discoveryClient.getInstances("testApplication")
                .stream()
                .map(si -> si.getUri())
                .findFirst();
    }*/

    @Autowired
    private AppConfig appConfig;

    @Override
    public void run(String... args) throws Exception {
        System.out.println();
        System.out.println("==============================");
        System.out.println("Config from consul: " + appConfig.getValues());
        //System.out.println("serviceUrl from consul: " + serviceUrl().get());
    }

    @Scheduled(fixedDelay = 1000)
    public void scheduled() {
        System.out.println("Config from consul: " + appConfig.getValues());
    }
}
