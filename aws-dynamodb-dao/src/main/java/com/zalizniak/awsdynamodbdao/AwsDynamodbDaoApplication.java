package com.zalizniak.awsdynamodbdao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * -Daws.profile=alex_zalizniak_com_dev
 */
@Slf4j
@Configuration
@SpringBootApplication
@EnableDynamoDBRepositories(basePackageClasses = UserRepository.class)
public class AwsDynamodbDaoApplication {

    public static void main(String[] args) {
        log.info("Hello..");
        SpringApplication.run(AwsDynamodbDaoApplication.class, args);
    }

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        return AmazonDynamoDBClientBuilder.standard().build();
    }

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository) {
        return args -> {
            userRepository.saveAll(Arrays.asList(
                    new User("kevin", "tst1"),
                    new User("josh long", "tst2"))
            );
            userRepository.findAll()
                    .forEach(e -> log.info("Found User: " + e));
        };
    }
}

