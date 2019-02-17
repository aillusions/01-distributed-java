package com.zaliznaik.javaasync;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Service
@EnableAsync
@Configuration
public class AsyncConf {

    @Async
    public CompletableFuture<String> sayHello() {
        return CompletableFuture.completedFuture("hello from future");
    }
}
