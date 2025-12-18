package com.example.demo.controller;

import com.example.demo.dto.AuthRequest;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public User signup(@RequestBody User user) {
        return userService.signup(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest auth) {
        return userService.login(auth.getUsername(), auth.getPassword());
    }
}
