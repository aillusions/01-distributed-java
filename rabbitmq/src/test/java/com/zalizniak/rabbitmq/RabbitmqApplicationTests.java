package com.zalizniak.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitmqApplicationTests {

    private static final String QUEUE_NAME = "my_queue";

    @Test
    public void contextLoads() {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("user");
        factory.setPassword("password");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "Hello World!";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");

        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
