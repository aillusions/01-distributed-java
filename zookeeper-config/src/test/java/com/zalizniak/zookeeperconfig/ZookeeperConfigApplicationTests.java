package com.zalizniak.zookeeperconfig;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ZookeeperConfigApplicationTests {

	@Autowired
	private AppFromZooKeeperConfig appFromZooKeeperConfig;

	@Autowired
	private AppFromZooKeeperProps appFromZooKeeperProps;

	@Test
	public void contextLoads() {
		log.info("custom.dj.config.property: " + appFromZooKeeperConfig.getProperty());

		Assert.assertEquals("Hello World", appFromZooKeeperConfig.getProperty());
		Assert.assertEquals("Hello World", appFromZooKeeperProps.getProperty());
	}
}
