package com.zalizniak.hazel;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IExecutorService;
import com.hazelcast.jet.Jet;
import com.hazelcast.jet.JetInstance;
import com.hazelcast.jet.pipeline.Pipeline;
import com.hazelcast.jet.pipeline.Sinks;
import com.hazelcast.jet.pipeline.Sources;
import org.junit.Assert;
import org.junit.Test;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import static com.hazelcast.jet.Traversers.traverseArray;
import static com.hazelcast.jet.aggregate.AggregateOperations.counting;
import static com.hazelcast.jet.function.Functions.wholeItem;

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

        IExecutorService exec = instance0.getExecutorService("Exec");
        exec.submit(new MyDistributedTask());
        exec.submit(new MyDistributedTask());
    }

    public static class MyDistributedTask implements Runnable, Serializable {

        @Override
        public void run() {
            System.out.println("Hello from task... from " + Thread.currentThread().getName());
        }
    }

    @Test
    public void testJet() {

        // Create the specification of the computation pipeline. Note
        // it's a pure POJO: no instance of Jet needed to create it.
        Pipeline p = Pipeline.create();
        p.drawFrom(Sources.<String>list("text"))
                .flatMap(word ->
                        traverseArray(word.toLowerCase().split("\\W+")))
                .filter(word -> !word.isEmpty())
                .groupingKey(wholeItem())
                .aggregate(counting())
                .drainTo(Sinks.map("counts"));

        // Start Jet, populate the input list
        JetInstance jet = Jet.newJetInstance();
        try {
            List<String> text = jet.getList("text");
            text.add("hello world hello hello world");
            text.add("world world hello world");

            // Perform the computation
            jet.newJob(p).join();

            // Check the results
            Map<String, Long> counts = jet.getMap("counts");
            System.out.println("Count of hello: "
                    + counts.get("hello"));
            System.out.println("Count of world: "
                    + counts.get("world"));
        } finally {
            Jet.shutdownAll();
        }

    }
}
