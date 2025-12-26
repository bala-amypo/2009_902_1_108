package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.model.User;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

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
        User user = userService.authenticateUser(request.getEmail(), request.getPassword());
        
        Authentication auth = new UsernamePasswordAuthenticationToken(
                user.getEmail(), request.getPassword(), Collections.emptyList());
        
        String token = jwtTokenProvider.generateToken(auth, user.getId(), user.getRole());
        
        return ApiResponse.success(new AuthResponse(token, user.getEmail(), user.getRole()));
    }
}