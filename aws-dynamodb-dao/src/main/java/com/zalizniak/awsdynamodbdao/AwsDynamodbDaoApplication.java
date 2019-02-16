package com.zalizniak.awsdynamodbdao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.UUID;

/**
 * -Daws.profile=alex_zalizniak_com_dev
 */
@Slf4j
@Configuration
@SpringBootApplication
public class AwsDynamodbDaoApplication {

    public static void main(String[] args) {
        log.info("Hello..");
        SpringApplication.run(AwsDynamodbDaoApplication.class, args);
    }

    // @Bean
    // CommandLineRunner commandLineRunner(UserRepository userRepository) {
    //     return args -> {
    //         userRepository.saveAll(Arrays.asList(
    //                 new User(UUID.randomUUID().toString(), "kevin", "tst1", 0),
    //                 new User(UUID.randomUUID().toString(), "josh long", "tst2", 0))
    //         );
    //         userRepository.findAll()
    //                 .forEach(e -> log.info("Found User: " + e));
    //     };
    // }
}

