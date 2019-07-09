package com.zalizniak.hashicorpvault;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.vault.core.VaultOperations;
import org.springframework.vault.core.VaultTransitOperations;

import javax.annotation.PostConstruct;

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

    @Autowired
    private VaultOperations vaultOperations;

    private VaultTransitOperations transitOperations;

    @PostConstruct
    public void postConstruct() {
        transitOperations = vaultOperations.opsForTransit();
    }

    @Override
    public void run(String... args) throws Exception {
        Logger logger = LoggerFactory.getLogger(HashicorpVaultApplication.class);

        logger.info("----------------------------------------");
        logger.info("Configuration properties");
        logger.info("        example.username is {}", configuration.getUsername());
        logger.info("        example.password is {}", configuration.getPassword());
        logger.info("----------------------------------------");

        String ciphertext = transitOperations.encrypt("my-key",
                "This is my secret string. Better not use umlauts here. "
                        + "This string is converted to bytes using String.getBytes() so "
                        + "make sure to set -Dfile.encoding=... properly.");

        System.out.println("Encrypted: " + ciphertext);

        String plaintext = transitOperations.decrypt("my-key", ciphertext);

        System.out.println("plaintext: " + plaintext);

    }
}
