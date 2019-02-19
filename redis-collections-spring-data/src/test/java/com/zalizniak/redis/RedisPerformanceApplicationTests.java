package com.zalizniak.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
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
public class RedisPerformanceApplicationTests {

    @Resource(name = "redisTemplate")
    private SetOperations<String, String> setOps;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Test
    public void testSetExpiration() {
        String key = "2019_b01";

        for (int i = 0; i < 3_000_000; i++) {
            String value = UUID.randomUUID().toString();
            setOps.add(key, value);
        }

        Assert.assertEquals(3_000_000L, (long) setOps.size(key));

        Date date = Date.from(LocalDateTime.now().plusSeconds(60).atZone(ZoneId.systemDefault()).toInstant());
        redisTemplate.expireAt(key, date);
    }
}

