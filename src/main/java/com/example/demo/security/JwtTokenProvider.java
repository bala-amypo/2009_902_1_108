package com.example.demo.security;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class JwtTokenProvider {

    private final String secret;
    private final long validity;
    private final boolean enabled;

    public JwtTokenProvider(String secret, long validity, boolean enabled) {
        this.secret = secret;
        this.validity = validity;
        this.enabled = enabled;
    }

    public String generateToken(Object authentication, Long userId, String role) {
        String email = authentication.toString();

        String raw =
                email + "|" +
                userId + "|" +
                role + "|" +
                System.currentTimeMillis();

        return Base64.getEncoder().encodeToString(raw.getBytes());
    }

    public boolean validateToken(String token) {
        try {
            Base64.getDecoder().decode(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        return (String) getAllClaims(token).get("email");
    }

    public Map<String, Object> getAllClaims(String token) {
        String decoded = new String(Base64.getDecoder().decode(token));
        String[] parts = decoded.split("\\|");

        Map<String, Object> claims = new HashMap<>();
        claims.put("email", parts[0]);
        claims.put("userId", Long.parseLong(parts[1]));
        claims.put("role", parts[2]);

        return claims;
    }
}
