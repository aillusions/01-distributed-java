package com.zalizniak.hazel;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;
import java.util.Queue;

/**
 *
 */
public class AppTest {
    /**
     *
     */
    @Test
    public void shouldAnswerWithTrue() {
        Config cfg = new Config();
        // Two nodes cluster
        HazelcastInstance instance0 = Hazelcast.newHazelcastInstance(cfg);
        HazelcastInstance instance1 = Hazelcast.newHazelcastInstance(cfg);

        Map<Integer, String> mapCustomers0 = instance0.getMap("customers");
        mapCustomers0.put(1, "Joe");
        mapCustomers0.put(2, "Ali");

        Map<Integer, String> mapCustomers1 = instance0.getMap("customers");
        Assert.assertEquals("Joe", mapCustomers1.get(1));

        Queue<String> queueCustomers = instance0.getQueue("customers");
        queueCustomers.offer("Tom");
        queueCustomers.offer("Mary");
        queueCustomers.offer("Jane");

        Assert.assertEquals("Tom", queueCustomers.poll());
        Assert.assertEquals("Mary", queueCustomers.peek());
        Assert.assertEquals(2, queueCustomers.size());
    }
}
