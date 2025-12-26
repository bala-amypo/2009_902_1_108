package com.example.demo.service.impl;

import com.example.demo.service.UserService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public Long register(String name, String email, String password, String role) {
        return 1L;
    }

    @Override
    public Map<String, Object> authenticateUser(String email, String password) {
        // minimal stub logic – enough for Spring + compilation
        Map<String, Object> response = new HashMap<>();
        response.put("userId", 1L);
        response.put("email", email);
        response.put("role", "ADMIN");
        return response;
    }
}
