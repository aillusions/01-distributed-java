package com.zalizniak.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ZookeeperApplicationTests {

    // Create: /configuration/DJ-Zookeeper/custom-dj-config-property
    @Value("${custom-dj-config-property}")
    private String property;

    @Test
    public void contextLoads() {
        log.info("custom-dj-config-property: " + property);
        Assert.assertEquals("Hello World", property);
    }
}

