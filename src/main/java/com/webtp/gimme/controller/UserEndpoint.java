package com.webtp.gimme.controller;

import com.webtp.gimme.model.User;
import com.webtp.gimme.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserEndpoint {
    
    @Autowired
    private UserService userService;

}
