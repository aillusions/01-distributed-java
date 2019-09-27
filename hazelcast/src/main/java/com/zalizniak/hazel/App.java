package com.zalizniak.hazel;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {

        Config cfg = new Config();
        HazelcastInstance instance = Hazelcast.newHazelcastInstance(cfg);

        System.out.println("Hello World!");
    }
}
