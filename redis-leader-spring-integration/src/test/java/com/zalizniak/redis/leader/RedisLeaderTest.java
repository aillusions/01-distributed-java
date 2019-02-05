package com.zalizniak.redis.leader;

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
public class RedisLeaderTest extends TestCase {

    @Autowired
    private LeadershipRegistrar leadershipRegistrar;

    @Test
    public void testLock() throws InterruptedException {
        Thread.sleep(2000);
        // TODO remove key from redis, instead of pause
        assertTrue(leadershipRegistrar.hasLeaderRole(RedisElectionConfig.COORDINATOR_LEADER_ROLE));
    }
}
