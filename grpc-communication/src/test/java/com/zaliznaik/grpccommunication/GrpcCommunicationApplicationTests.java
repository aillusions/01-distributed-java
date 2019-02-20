package com.zaliznaik.grpccommunication;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class GrpcCommunicationApplicationTests {

    @Autowired
    private GrpcClient helloWorldClient;

    @Test
    public void testSayHello() {
        Assert.assertEquals("Hello, John Doe", helloWorldClient.sayHello("John", "Doe"));
    }

    @Test
    public void testPerformance() {
        int requests = 1000;
        long start = System.currentTimeMillis();
        for (int i = 0; i < requests; i++) {
            helloWorldClient.sayHello("John", "Doe");
        }

        long spent = System.currentTimeMillis() - start;
        log.info("");
        log.info("Sent " + requests + " requests in: " + spent + " ms");
        log.info("At speed: " + ((double) spent / (double) requests) + " req / sec.");
    }
}
