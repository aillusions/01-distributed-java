package com.zalizniak.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.URL;

@Slf4j
@Service
public class LinksRedisList {

    //@Autowired
    //private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate;

    // inject the template as ListOperations
    @Resource(name = "redisTemplate")
    private ListOperations<String, String> listOps;

    public void addLink(String key, URL url) {
        listOps.leftPush(key, url.toExternalForm());
    }

    public long size(String key) {
        return listOps.size(key);
    }
}
