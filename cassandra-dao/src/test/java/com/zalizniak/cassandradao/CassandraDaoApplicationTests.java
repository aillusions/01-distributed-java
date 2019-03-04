package com.zalizniak.cassandradao;

import com.datastax.driver.core.utils.UUIDs;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class CassandraDaoApplicationTests {

    @Autowired
    private CustomerRepository repository;

    @Test
    public void contextLoads() {
        this.repository.deleteAll();

        // save a couple of customers
        for (int i = 0; i < 1; i++) {
            this.repository.save(new Customer(UUIDs.timeBased(), "Alice", "Smith"));
            this.repository.save(new Customer(UUIDs.timeBased(), "Bob", "Smith"));
        }

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

}

