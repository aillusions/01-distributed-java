package com.zalizniak.zookeeperconfig;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.stereotype.Component;

/**
 * /configuration/DJ-Zookeeper/custom.dj.config.property
 */
@Getter
@Component
public class AppFromZooKeeperConfig {

    @Autowired
    private StandardEnvironment environment;

    public String getProperty() {
        return environment.getProperty("custom.dj.config.property");
    }
}
