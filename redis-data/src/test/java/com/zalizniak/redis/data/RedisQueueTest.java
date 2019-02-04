package com.zalizniak.redis.data;

import com.zalizniak.redis.LinksRedisList;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisQueueTest extends TestCase {

    @Autowired
    private LinksRedisList linksRedisList;

    @Test
    public void test() throws MalformedURLException {
        String key = "123456";
        long oldSize = linksRedisList.size(key);
        linksRedisList.addLink(key, new URL("http://google.com"));
        assertEquals(oldSize + 1, linksRedisList.size(key));
    }
}