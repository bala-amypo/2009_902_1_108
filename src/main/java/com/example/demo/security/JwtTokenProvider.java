package com.example.demo.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JwtTokenProvider {

    private final Key key;
    private final long validityInMilliseconds;
    private final boolean debug;

    public JwtTokenProvider(String secret, long validityInMilliseconds, boolean debug) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.validityInMilliseconds = validityInMilliseconds;
        this.debug = debug;
    }

    // Generate token with claims: email, userId, role
    public String generateToken(Authentication authentication, Long userId, String role) {
        String email = authentication.getName();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setSubject(email)                // standard sub claim
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .claim("userId", userId)
                .claim("role", role)
                .claim("email", email)            // optional duplicate
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // Get all claims from token
    public Map<String, Object> getAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Get username (subject/email) safely
    public String getUsernameFromToken(String token) {
        Map<String, Object> claims = getAllClaims(token);
        return (String) claims.get("sub"); // use "sub" if setSubject used, or "email" if stored
    }

    // Validate token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            if (debug) {
                System.out.println("Invalid JWT: " + ex.getMessage());
            }
            return false;
        }
    }

    // Get userId claim
    public Long getUserIdFromToken(String token) {
        Map<String, Object> claims = getAllClaims(token);
        return ((Number) claims.get("userId")).longValue();
    }

    // Get role claim
    public String getRoleFromToken(String token) {
        Map<String, Object> claims = getAllClaims(token);
        return (String) claims.get("role");
    }
}
