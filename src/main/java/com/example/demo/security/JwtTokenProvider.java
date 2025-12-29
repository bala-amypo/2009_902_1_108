package com.example.demo.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component   // ⭐ THIS IS IMPORTANT
public class JwtTokenProvider {

    @Value("${jwt.secret:VerySecretKeyForJwtDemoApplication123456789ABCDEF0123456789ABCDEF}")
    private String secret;

    @Value("${jwt.expiration:3600000}")
    private long jwtExpirationMs;

    // Default constructor for Spring
    public JwtTokenProvider() {}

    // Constructor for tests
    public JwtTokenProvider(String secret, long jwtExpirationMs, boolean unused) {
        this.secret = secret != null ? secret : "VerySecretKeyForJwtDemoApplication123456789ABCDEF0123456789ABCDEF";
        this.jwtExpirationMs = jwtExpirationMs > 0 ? jwtExpirationMs : 3600000L;
    }

    public String generateToken(String username, String role, Long userId, String email) {
        String actualSecret = (secret != null && !secret.isEmpty()) ? secret : "VerySecretKeyForJwtDemoApplication123456789ABCDEF0123456789ABCDEF";
        long actualExpiration = (jwtExpirationMs > 0) ? jwtExpirationMs : 3600000L;
        
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .claim("userId", userId)
                .claim("email", email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + actualExpiration))
                .signWith(Keys.hmacShaKeyFor(actualSecret.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    // Overloaded method for backward compatibility with tests
    public String generateToken(Authentication authentication, Long userId, String role) {
        return generateToken(authentication.getName(), role, userId, authentication.getName());
    }

    public Jws<Claims> validateAndGetClaims(String token) {
        String actualSecret = (secret != null && !secret.isEmpty()) ? secret : "VerySecretKeyForJwtDemoApplication123456789ABCDEF0123456789ABCDEF";
        
        return Jwts.parserBuilder()
                .setSigningKey(actualSecret.getBytes())
                .build()
                .parseClaimsJws(token);
    }

    // Additional methods for backward compatibility with tests
    public String getUsernameFromToken(String token) {
        return validateAndGetClaims(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            validateAndGetClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Map<String, Object> getAllClaims(String token) {
        Claims claims = validateAndGetClaims(token).getBody();
        return new HashMap<>(claims);
    }
}