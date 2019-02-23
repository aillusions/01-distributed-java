package com.zalizniak.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.UUID;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisListTests {

    @Resource(name = "redisTemplate")
    private ListOperations<String, String> listOps;

    @Test
    public void testList() {
        String key = UUID.randomUUID().toString();
        String value = UUID.randomUUID().toString();

        Assert.assertEquals(0, (long) listOps.size(key));
        Assert.assertEquals(1, (long) listOps.leftPush(key, value));
        Assert.assertEquals(2, (long) listOps.rightPush(key, value));
        Assert.assertEquals(2, (long) listOps.size(key));
    }
}

