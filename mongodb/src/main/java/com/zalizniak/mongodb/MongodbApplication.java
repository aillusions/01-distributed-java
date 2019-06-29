package com.zalizniak.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;
import java.util.function.Consumer;

@SpringBootApplication
public class MongodbApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(MongodbApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        MongoCredential credential = MongoCredential.createCredential("admin", "local", "admin".toCharArray());

        MongoClient mongoClient = new MongoClient(new ServerAddress("localhost", 27017), Collections.singletonList(credential));

        MongoDatabase database = mongoClient.getDatabase("local");

        database.createCollection("customers", null);
        database.listCollectionNames().forEach((Consumer<? super String>) System.out::println);
    }
}
