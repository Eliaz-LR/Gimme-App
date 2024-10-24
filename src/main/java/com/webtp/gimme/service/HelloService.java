package com.webtp.gimme.service;

import com.webtp.gimme.model.Hello;
import com.webtp.gimme.repository.HelloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HelloService {

    @Autowired
    private HelloRepository helloRepository;

    public List<Hello> getHellos() {
        return helloRepository.findAll();
    }

    public void createHello(Hello hello) {
        helloRepository.save(hello);
    }
}
