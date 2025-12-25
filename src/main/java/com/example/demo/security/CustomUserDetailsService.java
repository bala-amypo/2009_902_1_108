package com.example.demo.security;

import com.example.demo.exception.BadRequestException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

public class CustomUserDetailsService implements UserDetailsService {

    private final Map<String, Map<String, Object>> users = new HashMap<>();
    private long nextId = 1L;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Map<String, Object> registerUser(String name, String email, String encodedPassword, String role) {
        if (users.containsKey(email)) {
            throw new BadRequestException("User already exists with email: " + email);
        }

        Map<String, Object> user = new HashMap<>();
        user.put("userId", nextId++);
        user.put("name", name);
        user.put("email", email);
        user.put("password", encodedPassword);
        user.put("role", role);

        users.put(email, user);
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Map<String, Object> user = users.get(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        return org.springframework.security.core.userdetails.User
                .withUsername((String) user.get("email"))
                .password((String) user.get("password"))
                .roles((String) user.get("role"))
                .build();
    }

    public boolean authenticate(String email, String rawPassword) {
        Map<String, Object> user = users.get(email);
        if (user == null) return false;
        return passwordEncoder.matches(rawPassword, (String) user.get("password"));
    }
}
