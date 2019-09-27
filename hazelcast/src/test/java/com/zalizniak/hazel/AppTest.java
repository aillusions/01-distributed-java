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
        HazelcastInstance instance = Hazelcast.newHazelcastInstance(cfg);

        Map<Integer, String> mapCustomers = instance.getMap("customers");
        mapCustomers.put(1, "Joe");
        mapCustomers.put(2, "Ali");

        Assert.assertEquals("Joe", mapCustomers.get(1));

        Queue<String> queueCustomers = instance.getQueue("customers");
        queueCustomers.offer("Tom");
        queueCustomers.offer("Mary");
        queueCustomers.offer("Jane");

        Assert.assertEquals("Tom", queueCustomers.poll());
        Assert.assertEquals("Mary", queueCustomers.peek());
        Assert.assertEquals(2, queueCustomers.size());
    }
}
