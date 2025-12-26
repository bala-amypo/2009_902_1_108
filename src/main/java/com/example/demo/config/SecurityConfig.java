package com.example.demo.config;

import com.example.demo.security.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public JwtTokenProvider jwtTokenProvider() {
        return new JwtTokenProvider(
                "0123456789ABCDEF0123456789ABCDEF", // 32 bytes
                3600000L
        );
    }
}
