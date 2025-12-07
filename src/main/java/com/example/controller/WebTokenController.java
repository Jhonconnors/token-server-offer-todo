package com.example.controller;

import com.example.contract.model.TokenResponse;
import com.example.exception.InvalidJwtException;
import com.example.service.ClientTokenValidationService;
import com.example.service.JwtGenerateService;
import com.example.service.RsaDecryptionService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.UUID;


@RestController
@RequestMapping("/auth")
@Slf4j
public class WebTokenController {

    private final ClientTokenValidationService validationService;
    private final JwtGenerateService generateService;
    private final RsaDecryptionService rsaDecryptionService;

    public WebTokenController(ClientTokenValidationService validationService,
                                   JwtGenerateService generateService, RsaDecryptionService rsaDecryptionService) {
        this.validationService = validationService;
        this.generateService = generateService;
        this.rsaDecryptionService = rsaDecryptionService;
    }

    @PostMapping("/web/token")
    public ResponseEntity<TokenResponse> generateToken(
            @RequestHeader("Authorization") String authorizationHeader) throws Exception {

        String clientJwt = authorizationHeader.substring("Bearer ".length());

        // 1. Parsear header & payload
        String[] parts = clientJwt.split("\\.");
        if (parts.length < 2) throw new InvalidJwtException("Invalid JWT");

        String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]));
        JsonNode payload = new ObjectMapper().readTree(payloadJson);

        String encCookies = payload.get("encCookies").asText();
        String encClientId = payload.get("encClientId").asText();

        // 2. Desencriptar con RSA privada
        String cookiesJson = rsaDecryptionService.decrypt(encCookies);
        String clientId = rsaDecryptionService.decrypt(encClientId);

        String scopes = validationService.validateClientIdOnly(UUID.fromString(clientId));
        if (scopes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // 4️⃣ Generar token del servidor (ejemplo simple, puedes usar tu servicio JWT del servidor)
        TokenResponse response = new TokenResponse();
        response.setAccessToken(generateService.generateSignedJwt(clientId, scopes, 2L));
        response.setTokenType("Bearer");
        response.setExpiresIn(200);
        response.scope(scopes);
        return ResponseEntity.ok(response);
    }
}
