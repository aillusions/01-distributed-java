package com.zalizniak.http2push;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.PushBuilder;

@Controller
public class PushController {

    @GetMapping(path = "/demoWithPush")
    public String demoWithPush(PushBuilder pushBuilder) {
        if (null != pushBuilder) {
            pushBuilder.path("resources/logo.png").push();
        }
        return "demo";
    }

    @GetMapping(path = "/demoWithoutPush")
    public String demoWithoutPush() {
        return "demo";
    }
}