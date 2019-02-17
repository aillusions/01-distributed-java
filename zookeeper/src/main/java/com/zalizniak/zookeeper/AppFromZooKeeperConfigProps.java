package com.zalizniak.zookeeper;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * /configuration/DJ-Zookeeper/custom.dj.config.property
 */
@Data
@Component
@ConfigurationProperties(prefix = "custom.dj.config")
public class AppFromZooKeeperConfigProps {

    private String property;
}
