package com.example.demo.service.impl;

import com.example.demo.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public Long register(String name, String email, String password, String role) {
        // minimal dummy logic (enough to satisfy Spring)
        return 1L;
    }
}
