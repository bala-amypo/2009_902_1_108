package com.example.demo.service.impl;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service   // ⭐ THIS IS CRITICAL
public class UserServiceImpl implements UserService {

    private final BCryptPasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();

    @Override
    public User register(User user) {
        // Minimal logic to satisfy AuthController
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return user;
    }

    @Override
    public User findByUsername(String username) {
        // Dummy implementation (enough for startup & controller)
        User user = new User();
        user.setUsername(username);
        user.setRole("USER");
        return user;
    }
}
