package com.zalizniak.zookeeperconfig;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@EnableScheduling
@SpringBootApplication
@EnableConfigurationProperties(AppFromZooKeeperProps.class)
public class ZookeeperConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZookeeperConfigApplication.class, args);
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
