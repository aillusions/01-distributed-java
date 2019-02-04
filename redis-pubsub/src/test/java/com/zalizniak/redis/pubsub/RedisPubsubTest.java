package com.zalizniak.redis.pubsub;

import com.zalizniak.redis.RedisMessageListener;
import com.zalizniak.redis.RedisMessageListener2;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisPubsubTest extends TestCase {

    @Autowired
    private RedisMessageListener redisMessageListener1;

    @Autowired
    private RedisMessageListener2 redisMessageListener2;

    @Test
    public void test() throws InterruptedException {
        Thread.sleep(500);

        assertTrue(redisMessageListener1.getMessages().size() > 0);
        assertTrue(redisMessageListener2.getMessages().size() > 0);

        assertEquals(redisMessageListener1.getMessages().size(),
                redisMessageListener2.getMessages().size());
    }
}
