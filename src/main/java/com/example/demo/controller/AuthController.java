package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @PostMapping("/register")
    public ApiResponse<AuthResponse> register(@RequestBody RegisterRequest request) {
        // Mock response with hardcoded token for testing
        String mockToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0QGV4YW1wbGUuY29tIiwicm9sZSI6IlVTRVIiLCJ1c2VySWQiOjEsImVtYWlsIjoidGVzdEBleGFtcGxlLmNvbSIsImlhdCI6MTY0MDk5NTIwMCwiZXhwIjoxNjQwOTk4ODAwfQ.mockSignature";
        return ApiResponse.success(new AuthResponse(mockToken, request.getEmail(), request.getRole()));
    }
    
    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@RequestBody AuthRequest request) {
        // Mock response with hardcoded token for testing
        String mockToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0QGV4YW1wbGUuY29tIiwicm9sZSI6IkFETUlOIiwidXNlcklkIjoxLCJlbWFpbCI6InRlc3RAZXhhbXBsZS5jb20iLCJpYXQiOjE2NDA5OTUyMDAsImV4cCI6MTY0MDk5ODgwMH0.mockSignature";
        return ApiResponse.success(new AuthResponse(mockToken, request.getEmail(), "ADMIN"));
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