package com.zaliznaik.javaasync;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class JavaAsyncApplicationTests {

    @Autowired
    private AsyncConf asyncConf;

    @Test
    public void contextLoads0() throws ExecutionException, InterruptedException {

        CompletableFuture<String> say1 = asyncConf.sayHello();
        CompletableFuture<String> say2 = asyncConf.sayHello();

        CompletableFuture.allOf(say1, say2).join();

        Assert.assertEquals("hello from future", say1.get());
        Assert.assertEquals("hello from future", say2.get());
    }

    @Test
    public void contextLoads1() {

        CompletableFuture<String> say1 = asyncConf.sayHello().thenApplyAsync(s -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("say1: " + s);
            return s;
        });
    }

}
