package com.webtp.gimme.controller;

import com.webtp.gimme.model.Hello;
import com.webtp.gimme.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HelloEndpoint {

    @Autowired
    private HelloService helloService;

    @GetMapping("/hello")
    public List<Hello> hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return helloService.getHellos();
    }

    @PostMapping("/hello")
    public String helloPost(@RequestBody Hello hello) {
        helloService.createHello(hello);
        return "Hello crée";
    }
}
