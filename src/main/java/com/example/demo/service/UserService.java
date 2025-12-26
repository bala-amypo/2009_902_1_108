package com.example.demo.service;

import java.util.Map;

public interface UserService {

    Long register(String name, String email, String password, String role);

    Map<String, Object> authenticateUser(String email, String password);
}
