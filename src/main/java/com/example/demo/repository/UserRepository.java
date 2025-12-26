package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserRepository {
    
    private final Map<String, User> users = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);
    
    public User save(User user) {
        if (user.getId() == null) {
            user.setId(idCounter.getAndIncrement());
        }
        users.put(user.getEmail(), user);
        return user;
    }
    
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(users.get(email));
    }
    
    public boolean existsByEmail(String email) {
        return users.containsKey(email);
    }
}