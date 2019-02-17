package com.zalizniak.zookeeper;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.endpoint.event.RefreshEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.stereotype.Service;

/**
 * Create:
 * /configuration/DJ-Zookeeper/custom-dj-config-property
 * With value: Hello World
 */
@Slf4j
@Getter
@RefreshScope
@Service
public class AppFromZooKeeperConfig {

    @Autowired
    private StandardEnvironment environment;

    // @Value("${custom-dj-config-property}")
    // private String property;

    public String getProperty() {
        return environment.getProperty("custom-dj-config-property");
    }

    @EventListener(RefreshEvent.class)
    public void onRefreshEvent(RefreshEvent evt) {
        log.info("RefreshEvent: " + evt);
    }
}
