package com.zalizniak.bloomfilter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.Serializable;
import java.util.UUID;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisBloomFilterRedissonApplicationTests {


    @Autowired
    private RedissonClient redisson;

    // 4294967296 - Number of contained bits is limited to 2^32
    //  729844083 - size
    @Test
    public void testForString() {

        String bloomFilterKey = UUID.randomUUID().toString();

        RBloomFilter<String> bloomFilter = redisson.getBloomFilter(bloomFilterKey);
        bloomFilter.tryInit(100_000_000, 0.03);

        bloomFilter.add("a");
        bloomFilter.add("b");
        bloomFilter.add("c");
        bloomFilter.add("d");

        log.info("expectedInsertions: " + bloomFilter.getExpectedInsertions());
        log.info("falseProbability: " + bloomFilter.getFalseProbability());
        log.info("count: " + bloomFilter.count());
        log.info("Size: " + bloomFilter.getSize());

        assertTrue(bloomFilter.contains("a"));
        assertFalse(bloomFilter.contains("f"));
    }

    @Test
    public void testForObject() {

        String bloomFilterKey = UUID.randomUUID().toString();

        RBloomFilter<BloomData> bloomFilter = redisson.getBloomFilter(bloomFilterKey);
        bloomFilter.tryInit(100_000_000, 0.03);

        bloomFilter.add(new BloomData("123456"));
        bloomFilter.add(new BloomData("456789"));
        bloomFilter.add(new BloomData("111222"));
        bloomFilter.add(new BloomData("333444"));

        log.info("expectedInsertions: " + bloomFilter.getExpectedInsertions());
        log.info("falseProbability: " + bloomFilter.getFalseProbability());
        log.info("count: " + bloomFilter.count());
        log.info("Size: " + bloomFilter.getSize());

        assertTrue(bloomFilter.contains(new BloomData("333444")));
        assertFalse(bloomFilter.contains(new BloomData("000000")));
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BloomData implements Serializable {
        private String value;
    }
}

