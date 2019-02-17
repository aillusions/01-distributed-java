package com.zalizniak.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.cloud.endpoint.event.RefreshEvent;
import org.springframework.cloud.endpoint.event.RefreshEventListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Why this class needed?
 */
@Slf4j
@Service
public class CustomRefreshEventListener extends RefreshEventListener {

    @Autowired
    public CustomRefreshEventListener(ContextRefresher refresh) {
        super(refresh);
        super.handle(new ApplicationReadyEvent(new SpringApplication(), null, null));
    }

    @EventListener
    public void handle(RefreshEvent event) {
        super.handle(event);
    }
}
