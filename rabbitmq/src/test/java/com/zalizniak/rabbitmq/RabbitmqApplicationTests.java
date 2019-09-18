package com.zalizniak.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeoutException;

/**
 * TODO see more https://github.com/rabbitmq/rabbitmq-tutorials/tree/master/java
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitmqApplicationTests {

    private static final String QUEUE_NAME = "my_queue";

    private static ConnectionFactory factory;

    @BeforeClass
    public static void setClass() {
        factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("user");
        factory.setPassword("password");
    }

    @Test
    public void testSend() {

        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {

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

    @Test
    public void testReceive() throws IOException, TimeoutException, InterruptedException {

        CountDownLatch doneSignal = new CountDownLatch(1);

        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            System.out.println(" [*] Waiting for messages.");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
                doneSignal.countDown();
            };

            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
            });

            doneSignal.await();
        }
    }
}
