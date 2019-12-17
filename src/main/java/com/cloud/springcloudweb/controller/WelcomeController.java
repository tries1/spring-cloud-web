package com.cloud.springcloudweb.controller;

import com.cloud.springcloudweb.repository.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class WelcomeController {

    @Value("${server.welcome.message}")
    private String welcomeMessage;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping({"", "/", "index.html"})
    public String home(){

        /*customerRepository.findByLastName("glenn")
                .doOnNext(customer -> System.out.println(customer.toString()))
                .subscribe();*/
        customerRepository.findByLastName("glenn").stream().forEach(customer -> System.out.println(customer.toString()));

        return welcomeMessage;
    }
}
