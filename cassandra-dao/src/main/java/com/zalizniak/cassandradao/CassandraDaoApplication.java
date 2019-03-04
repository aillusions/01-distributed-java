package com.zalizniak.cassandradao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 */
@Slf4j
@SpringBootApplication
public class CassandraDaoApplication implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        log.info("Started.");
    }

    public static void main(String[] args) {
        SpringApplication.run(CassandraDaoApplication.class, args);
    }
}

