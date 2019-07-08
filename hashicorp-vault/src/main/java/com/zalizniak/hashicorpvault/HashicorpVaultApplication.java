package com.zalizniak.hashicorpvault;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(MyConfiguration.class)
public class HashicorpVaultApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(HashicorpVaultApplication.class, args);
    }

    private final MyConfiguration configuration;

    public HashicorpVaultApplication(MyConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void run(String... args) throws Exception {
        Logger logger = LoggerFactory.getLogger(HashicorpVaultApplication.class);

        logger.info("----------------------------------------");
        logger.info("Configuration properties");
        logger.info("        example.username is {}", configuration.getUsername());
        logger.info("        example.password is {}", configuration.getPassword());
        logger.info("----------------------------------------");
    }
}
