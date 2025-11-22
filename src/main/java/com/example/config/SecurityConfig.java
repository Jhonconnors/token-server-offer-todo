package com.example.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean
    public Argon2PasswordEncoder passwordEncoder() {
        // parámetros: saltLen, hashLen, parallelism, memory, iterations
        return new Argon2PasswordEncoder(16, 32, 1, 1 << 12, 3);
    }
}
