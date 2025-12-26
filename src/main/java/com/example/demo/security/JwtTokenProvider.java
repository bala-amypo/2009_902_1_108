package com.example.demo.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

public class JwtTokenProvider {

    private final String secret;
    private final long jwtExpirationMs;

    public JwtTokenProvider(String secret, long jwtExpirationMs) {
        this.secret = secret;
        this.jwtExpirationMs = jwtExpirationMs;
    }

    /* =====================================================
       1️⃣ USED BY TEST CASES
       ===================================================== */
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

    /* =====================================================
       2️⃣ USED BY AuthController
       ===================================================== */
    public String generateToken(Authentication authentication, Long userId, String email) {

        Object principal = authentication.getPrincipal();
        String username;
        String role = "USER"; // default safe role

        if (principal instanceof UserDetails userDetails) {
            username = userDetails.getUsername();
            if (!userDetails.getAuthorities().isEmpty()) {
                role = userDetails.getAuthorities().iterator().next().getAuthority();
            }
        } else {
            username = principal.toString();
        }

        return generateToken(username, role, userId, email);
    }

    /* =====================================================
       3️⃣ USED BY JwtAuthenticationFilter
       ===================================================== */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    /* =====================================================
       4️⃣ USED INTERNALLY (OPTIONAL)
       ===================================================== */
    public Jws<Claims> validateAndGetClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseClaimsJws(token);
    }
}
