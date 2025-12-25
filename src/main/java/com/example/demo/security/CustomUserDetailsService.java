package com.example.demo.security;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class CustomUserDetailsService {

    private static final Map<String, Map<String, Object>> USERS = new HashMap<>();
    private static final AtomicLong ID_GEN = new AtomicLong(1);

    public Map<String, Object> registerUser(
            String name,
            String email,
            String password,
            String role
    ) {
        Map<String, Object> user = new HashMap<>();
        user.put("userId", ID_GEN.getAndIncrement());
        user.put("name", name);
        user.put("email", email);
        user.put("password", password);
        user.put("role", role);

        USERS.put(email, user);
        return user;
    }

    public Map<String, Object> loadUserByUsername(String email) {
        Map<String, Object> user = USERS.get(email);
        if (user == null) {
            throw new RuntimeException("User not found: " + email);
        }
        return user;
    }
}
