package com.example.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {

    private final String jwtSecret = "yourSecretKey"; // Replace with your secure secret
    private final long jwtExpirationInMs = 86400000;  // 1 day
    private final boolean enableClaims = true;        // Enable extra claims if needed

    // Generate JWT with optional claims
    public String generateToken(Authentication authentication, Long userId, String role) {
        Map<String, Object> claims = new HashMap<>();
        if (enableClaims) {
            claims.put("userId", userId);
            claims.put("role", role);
            claims.put("email", authentication.getName());
        }

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(authentication.getName())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    // Extract username/email from token
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return (String) claims.get("email");
    }

    // Get all claims as a Map
    public Map<String, Object> getAllClaims(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return new HashMap<>(claims);
    }

    // Validate token
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
