package com.zalizniak.websocketwithredis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * @author aillusions
 */
@Slf4j
@Controller
public class GreetingController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        //Thread.sleep(1000); // simulated delay
        log.info("Greeting request from: " +  message.getName());
        return new Greeting("Hello, " + message.getName() + "!");
    }
}
