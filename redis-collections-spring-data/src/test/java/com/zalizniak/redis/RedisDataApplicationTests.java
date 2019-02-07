package com.zalizniak.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisDataApplicationTests {

    @Resource(name = "redisTemplate")
    private ListOperations<String, String> listOps;

    @Test
    public void testList() {
        String key = "123456";
        long oldSize = listOps.size(key);
        Long index = listOps.leftPush(key, "google.com");
        Assert.assertTrue(index >= 0);
        Assert.assertEquals(oldSize + 1, (long) listOps.size(key));
        log.info("Added element to index: " + index);
    }

}

