package com.example.demo.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    
    private final Map<String, Map<String, Object>> users = new HashMap<>();
    private final AtomicLong userIdCounter = new AtomicLong(1);
    
    public Map<String, Object> createUser(String name, String email, String encodedPassword, String role) {
        Map<String, Object> user = new HashMap<>();
        user.put("userId", userIdCounter.getAndIncrement());
        user.put("name", name);
        user.put("email", email);
        user.put("password", encodedPassword);
        user.put("role", role);
        
        users.put(email, user);
        return user;
    }
    
    public com.example.demo.model.User authenticateUser(String email, String password) {
        Map<String, Object> userMap = users.get(email);
        if (userMap == null) {
            throw new RuntimeException("Invalid credentials");
        }
        
        // Convert Map to User object for compatibility
        com.example.demo.model.User user = new com.example.demo.model.User();
        user.setId((Long) userMap.get("userId"));
        user.setName((String) userMap.get("name"));
        user.setEmail((String) userMap.get("email"));
        user.setPassword((String) userMap.get("password"));
        user.setRole((String) userMap.get("role"));
        
        return user;
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Map<String, Object> user = users.get(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        
        return User.builder()
                .username(username)
                .password((String) user.get("password"))
                .authorities(Collections.emptyList())
                .build();
    }
}