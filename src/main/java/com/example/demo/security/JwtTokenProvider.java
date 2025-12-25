package com.example.demo.security;

import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    public String generateToken(String username) {
        return "dummy-jwt-token-for-" + username;
    }

    public boolean validateToken(String token) {
        return token != null && token.startsWith("dummy-jwt-token");
    }

    public String getUsernameFromToken(String token) {
        return token.replace("dummy-jwt-token-for-", "");
    }
}
