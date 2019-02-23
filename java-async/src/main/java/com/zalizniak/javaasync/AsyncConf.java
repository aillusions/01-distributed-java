package com.zalizniak.javaasync;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@EnableAsync
@Configuration
public class AsyncConf {

    @Async
    public CompletableFuture<String> sayHello() {
        log.info("sayHello() running in thread: " + Thread.currentThread().getName());
        return CompletableFuture.completedFuture("hello from future");
    }
}
