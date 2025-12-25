package com.example.demo.service.impl;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repo;

    public UserServiceImpl(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public User createUser(User user) {
        if (repo.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists: " + user.getUsername());
        }
        if (repo.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists: " + user.getEmail());
        }
        return repo.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));
    }

    @Override
    public List<User> getAllUsers() {
        return repo.findAll();
    }

    @Override
    public User updateUser(Long id, User user) {
        User existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));

        existing.setUsername(user.getUsername());
        existing.setEmail(user.getEmail());
        existing.setPassword(user.getPassword());
        return repo.save(existing);
    }

    @Override
    public void deleteUser(Long id) {
        User existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));
        repo.delete(existing);
    }

    @Override
    public User getUserByUsername(String username) {
        return repo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return repo.findByEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repo.existsByEmail(email);
    }
}
