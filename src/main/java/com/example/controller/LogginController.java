package com.example.auth.controller;

import com.example.auth.KeyUtils;
import com.example.auth.model.UserEntity;
import com.example.auth.repository.UserRepository;
import com.example.auth.service.JwtService;
import com.example.auth.service.KeyService;
import com.example.contract.model.LoginRequest;
import com.example.contract.model.LoginResponse;
import com.example.exception.LoginFailedException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    private final KeyService keyService;
    private final UserRepository userRepository;
    private final Argon2PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final DateTimeFormatter minuteFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    public AuthController(KeyService keyService, UserRepository userRepository,
                          Argon2PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.keyService = keyService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    @Transactional
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest body) {
        try {
            if (body.getUsername() == null || body.getPassword() == null) {
                throw new LoginFailedException("Username or password missing");
            }

            // 1) Decode base64 -> RSA cipher
            byte[] userCipher = Base64.getDecoder().decode(body.getUsername());
            byte[] passCipher = Base64.getDecoder().decode(body.getPassword());

            // 2) Decrypt RSA OAEP SHA-512
            byte[] userPlainBytes = KeyUtils.rsaOaepDecrypt(keyService.getPrivateKey(), userCipher);
            byte[] passPlainBytes = KeyUtils.rsaOaepDecrypt(keyService.getPrivateKey(), passCipher);

            String userInnerBase64 = new String(userPlainBytes, StandardCharsets.UTF_8);
            String passInnerBase64 = new String(passPlainBytes, StandardCharsets.UTF_8);

            // 3) Decode inner base64 to "text_+_YYYY-MM-DDTHH:MM"
            String userCombined = new String(Base64.getDecoder().decode(userInnerBase64), StandardCharsets.UTF_8);
            String passCombined = new String(Base64.getDecoder().decode(passInnerBase64), StandardCharsets.UTF_8);

            // 4) Split
            String[] up = userCombined.split("_\\+_");
            String[] pp = passCombined.split("_\\+_");
            if (up.length < 2 || pp.length < 2) {
                throw new LoginFailedException("Malformed username or password");
            }
            String username = up[0];
            String userTime = up[1];
            String password = pp[0];
            String passTime = pp[1];

            // 5) Validate timestamps: max diff 1 minute
            Instant now = Instant.now();
            Instant sentUser = LocalDateTime.parse(userTime, minuteFmt).toInstant(ZoneOffset.UTC);
            Instant sentPass = LocalDateTime.parse(passTime, minuteFmt).toInstant(ZoneOffset.UTC);

            long diffUser = Math.abs(Duration.between(sentUser, now).toMinutes());
            long diffPass = Math.abs(Duration.between(sentPass, now).toMinutes());

            if (diffUser > 1 || diffPass > 1) {
                throw new LoginFailedException("Timestamp too old or skewed");
            }

            // 6) Lookup user and validate password
            Optional<UserEntity> opt = userRepository.findByUsername(username);
            if (opt.isEmpty()) {
                throw new LoginFailedException("USER_NOT_FOUND");
            }
            UserEntity user = opt.get();
            if (!passwordEncoder.matches(password, user.getPasswordHash())) {
                throw new LoginFailedException("Error in Credentials");
            }

            // 7) Generate JWT
            String jwt = jwtService.createToken(user.getUsername(), Map.of("sub", user.getUsername()));

            // 8) Build LoginResponse
            LoginResponse response = new LoginResponse();
            response.setAccessToken(jwt);
            response.setTokenType("Bearer");
            response.setExpiresIn(300); // 5 minutes

            return ResponseEntity.ok(response);

        } catch (Exception ex) {
            log.error("Login error", ex);
            throw new LoginFailedException("Error processing login request");
        }
    }
}
