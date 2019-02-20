package com.zaliznaik.grpccommunication;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GrpcCommunicationApplicationTests {

    @Autowired
    private GrpcClient helloWorldClient;

    @Test
    public void testSayHello() {
        Assert.assertEquals("Hello, John Doe", helloWorldClient.sayHello("John", "Doe"));
    }
}
