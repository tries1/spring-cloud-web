package com.cloud.springcloudweb.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RefreshScope
@RestController
public class WelcomeController {

    @Value("${server.welcome.message:empty welcome message}")
    private String welcomeMessage;

    @GetMapping({"", "/", "index.html"})
    public String home() {
        return welcomeMessage;
    }

    @GetMapping("error")
    public Mono<String> errorTest() {
        int a = 1 / 0;
        return Mono.just("eRRor!!");
    }
}
