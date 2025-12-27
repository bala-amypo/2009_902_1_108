package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.model.User;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    
    public AuthController(UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }
    
    @PostMapping("/register")
    public ApiResponse<AuthResponse> register(@RequestBody RegisterRequest request) {
        User user = userService.registerUser(request.getName(), request.getEmail(), request.getPassword(), request.getRole());
        String token = jwtTokenProvider.generateToken(user.getEmail(), user.getRole(), user.getId(), user.getEmail());
        return ApiResponse.success(new AuthResponse(token, user.getEmail(), user.getRole()));
    }
    
    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@RequestBody AuthRequest request) {
        Object result = userService.authenticateUser(request.getEmail(), request.getPassword());
        
        User user;
        if (result instanceof Map) {
            // Handle Map<String, Object> case
            @SuppressWarnings("unchecked")
            Map<String, Object> userMap = (Map<String, Object>) result;
            user = new User();
            user.setId((Long) userMap.get("userId"));
            user.setName((String) userMap.get("name"));
            user.setEmail((String) userMap.get("email"));
            user.setPassword((String) userMap.get("password"));
            user.setRole((String) userMap.get("role"));
        } else {
            // Handle User object case
            user = (User) result;
        }
        
        String token = jwtTokenProvider.generateToken(user.getEmail(), user.getRole(), user.getId(), user.getEmail());
        
        return ApiResponse.success(new AuthResponse(token, user.getEmail(), user.getRole()));
    }
    
    public static class RegisterRequest {
        private String name;
        private String email;
        private String password;
        private String role;
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }
    }
}