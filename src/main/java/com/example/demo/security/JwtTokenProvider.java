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

    @Value("${jwt.secret:0123456789ABCDEF0123456789ABCDEF}")
    private String secret;

    @Value("${jwt.expiration:3600000}")
    private long jwtExpirationMs;

    public String generateToken(String username, String role, Long userId, String email) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .claim("userId", userId)
                .claim("email", email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    // Overloaded method for backward compatibility with tests
    public String generateToken(Authentication authentication, Long userId, String role) {
        return generateToken(authentication.getName(), role, userId, authentication.getName());
    }

    public Jws<Claims> validateAndGetClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secret.getBytes())
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