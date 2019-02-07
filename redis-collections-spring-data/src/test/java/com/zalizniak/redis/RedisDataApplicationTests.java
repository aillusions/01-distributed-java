package com.zalizniak.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.net.MalformedURLException;
import java.net.URL;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisDataApplicationTests {

    //@Autowired
    //private StringRedisTemplate redisTemplate;

    // inject the template as ListOperations
    @Resource(name = "redisTemplate")
    private ListOperations<String, String> listOps;

    @Test
    public void test() throws MalformedURLException {
        String key = "123456";
        long oldSize = size(key);
        addLink(key, new URL("http://google.com"));
        Assert.assertEquals(oldSize + 1, size(key));
    }

    public void addLink(String key, URL url) {
        listOps.leftPush(key, url.toExternalForm());
    }

    public long size(String key) {
        return listOps.size(key);
    }
}

