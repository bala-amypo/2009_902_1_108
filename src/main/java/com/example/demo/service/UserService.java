package com.example.demo.service;

import com.example.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(User user);
    User getUserById(Long id);
    List<User> getAllUsers();
    User updateUser(Long id, User user);
    void deleteUser(Long id);
    User getUserByUsername(String username);
    Optional<User> getUserByEmail(String email);  // Added to match repo
    boolean existsByEmail(String email);
}
