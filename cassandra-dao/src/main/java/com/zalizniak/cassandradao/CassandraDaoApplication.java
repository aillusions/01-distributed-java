package com.zalizniak.cassandradao;

import com.datastax.driver.core.utils.UUIDs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * docker exec -it dj-cassandra-srv cqlsh
 *
 * CREATE KEYSPACE mykeyspace WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };
 *
 * CREATE TABLE mykeyspace.customer (id TimeUUID PRIMARY KEY, firstname text, lastname text);
 * CREATE INDEX customerfistnameindex ON mykeyspace.customer (firstname);
 * CREATE INDEX customersecondnameindex ON mykeyspace.customer (lastname);
 *
 */
@Slf4j
@SpringBootApplication
public class CassandraDaoApplication implements CommandLineRunner {

    @Autowired
    private CustomerRepository repository;

    @Override
    public void run(String... args) throws Exception {

        this.repository.deleteAll();

        // save a couple of customers
        this.repository.save(new Customer(UUIDs.timeBased(), "Alice", "Smith"));
        this.repository.save(new Customer(UUIDs.timeBased(), "Bob", "Smith"));

        // fetch all customers
        log.info("Customers found with findAll():");
        log.info("-------------------------------");
        for (Customer customer : this.repository.findAll()) {
            log.info("customer:" + customer);
        }
        log.info("");

        // fetch an individual customer
        log.info("Customer found with findByFirstName('Alice'):");
        log.info("--------------------------------");
        log.info("findByFirstName: " + this.repository.findByFirstName("Alice"));

        log.info("Customers found with findByLastName('Smith'):");
        log.info("--------------------------------");
        for (Customer customer : this.repository.findByLastName("Smith")) {
            log.info("customer:" + customer);
        }

        log.info("CommandLineRunner executed.");
    }

    public static void main(String[] args) {
        SpringApplication.run(CassandraDaoApplication.class, args);
    }

}

