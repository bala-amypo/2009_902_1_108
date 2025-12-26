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
    
    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@RequestBody AuthRequest request) {
        Object result = userService.authenticateUser(request.getEmail(), request.getPassword());
        
        User user;
        if (result instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Object> userMap = (Map<String, Object>) result;
            user = new User();
            user.setId((Long) userMap.get("userId"));
            user.setName((String) userMap.get("name"));
            user.setEmail((String) userMap.get("email"));
            user.setPassword((String) userMap.get("password"));
            user.setRole((String) userMap.get("role"));
        } else {
            user = (User) result;
        }
        
        String token = jwtTokenProvider.generateToken(user.getEmail(), user.getRole(), user.getId(), user.getEmail());
        
        return ApiResponse.success(new AuthResponse(token, user.getEmail(), user.getRole()));
    }
}