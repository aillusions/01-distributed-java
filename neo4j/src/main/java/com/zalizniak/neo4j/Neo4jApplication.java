package com.zalizniak.neo4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Neo4jApplication implements CommandLineRunner {


    @Autowired
    private HelloWorldExample helloWorldExample;

    public static void main(String[] args) {
        SpringApplication.run(Neo4jApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Started.");
        helloWorldExample.printGreeting("hello");
    }

}
