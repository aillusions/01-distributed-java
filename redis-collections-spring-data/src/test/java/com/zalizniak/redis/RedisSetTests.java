package com.zalizniak.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisSetTests {

    @Resource(name = "redisTemplate")
    private SetOperations<String, String> setOps;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void testSetExpiration() {
        String key = UUID.randomUUID().toString();
        String value = UUID.randomUUID().toString();
        Assert.assertEquals(1, (long) setOps.add(key, value));
        Date date = java.util.Date.from(LocalDateTime.now().plusSeconds(60).atZone(ZoneId.systemDefault()).toInstant());
        redisTemplate.expireAt(key, date);
    }

    @Test
    public void testSet() {
        String key = UUID.randomUUID().toString();
        String value = UUID.randomUUID().toString();

        Assert.assertEquals(0, (long) setOps.size(key));
        Assert.assertEquals(1, (long) setOps.add(key, value));
        Assert.assertEquals(0, (long) setOps.add(key, value));
        Assert.assertEquals(1, (long) setOps.size(key));
    }
}

