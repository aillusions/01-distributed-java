package com.zalizniak.bloomfilter;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisBloomFilterRedissonApplicationTests {


    @Autowired
    private RedissonClient redisson;

    @Test
    public void contextLoads() {

        RBloomFilter<String> bloomFilter = redisson.getBloomFilter("RedisBloomFilterRedisson");
        bloomFilter.tryInit(100_000_000, 0.03);

        bloomFilter.add("a");
        bloomFilter.add("b");
        bloomFilter.add("c");
        bloomFilter.add("d");

        log.info("expectedInsertions: " + bloomFilter.getExpectedInsertions());
        log.info("falseProbability: " + bloomFilter.getFalseProbability());
        log.info("hashIterations: " + bloomFilter.getHashIterations());
        log.info("contains(\"a\"): " + bloomFilter.contains("a"));
        log.info("count: " + bloomFilter.count());
    }
}

