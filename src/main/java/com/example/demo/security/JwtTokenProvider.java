package com.example.demo.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;

import java.security.Key;
import java.util.Date;
import java.util.Map;

public class JwtTokenProvider {

    private final Key key;
    private final long validityInMs;
    private final boolean enabled;

    public JwtTokenProvider(String secret, long validityInMs, boolean enabled) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.validityInMs = validityInMs;
        this.enabled = enabled;
    }

    public String generateToken(Authentication authentication, Long userId, String role) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + validityInMs);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim("userId", userId)
                .claim("role", role)
                .claim("email", authentication.getName())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        return getAllClaims(token).getSubject();
    }

    public Map<String, Object> getAllClaims(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }
}
