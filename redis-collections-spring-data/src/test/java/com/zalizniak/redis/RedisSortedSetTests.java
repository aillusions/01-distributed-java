package com.zalizniak.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.UUID;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisSortedSetTests {

    @Resource(name = "redisTemplate")
    private ZSetOperations<String, String> zSetOps;

    @Test
    public void testZSet() {
        String key = UUID.randomUUID().toString();
        String value1 = UUID.randomUUID().toString();
        String value2 = UUID.randomUUID().toString();

        Assert.assertEquals(0, (long) zSetOps.size(key));

        Assert.assertTrue(zSetOps.add(key, value1, 100));
        Assert.assertFalse(zSetOps.add(key, value1, 1000));

        Assert.assertTrue(zSetOps.add(key, value2, 10));

        Assert.assertEquals(2, (long) zSetOps.size(key));

        Assert.assertEquals(value2, new LinkedList<>(zSetOps.range(key, 0, 1)).get(0));
    }
}

