package com.zalizniak.activemq.jms;

import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class JmsReceiveTest extends TestCase {

    @Autowired
    private AmqJmsListener amqJmsListener;

    @Test
    public void testReceive() throws InterruptedException {
        Thread.sleep(5000);
        assertTrue(amqJmsListener.getReceived().get() > 0);
    }
}
