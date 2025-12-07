package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // desactiva CSRF si no lo necesitas
                .authorizeHttpRequests(auth -> auth
                        // endpoints públicos
                        .requestMatchers("/auth/login").permitAll()
                        .requestMatchers("/auth/web/token").permitAll()
                        .requestMatchers("/certs").permitAll()
                        .requestMatchers("/token").permitAll()
                        // cualquier otro endpoint requiere autenticación
                        .anyRequest().authenticated()
                );
        return http.build();
    }

    @Bean
    public Argon2PasswordEncoder passwordEncoder() {
        // parámetros: saltLen, hashLen, parallelism, memory, iterations
        return new Argon2PasswordEncoder(16, 32, 1, 1 << 12, 3);
    }

}
