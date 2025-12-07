package com.example.controller;

import com.example.contract.api.TokenApi;
import com.example.contract.model.TokenRequest;
import com.example.contract.model.TokenResponse;
import com.example.service.ClientTokenValidationService;
import com.example.service.JwtGenerateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Validated
public class GenerateTokenController implements TokenApi {

    private final ClientTokenValidationService validationService;
    private final JwtGenerateService generateService;

    public GenerateTokenController(ClientTokenValidationService validationService,
                                   JwtGenerateService generateService) {
        this.validationService = validationService;
        this.generateService = generateService;
    }

    @Override
    public ResponseEntity<TokenResponse> tokenPost(String authorization, TokenRequest tokenRequest) {
        try {
            // 1️⃣ Extraer token del header Authorization (Bearer xxx)
            String[] parts = authorization.split(" ");
            if (parts.length != 2 || !"Bearer".equalsIgnoreCase(parts[0])) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            String clientJwt = parts[1];

            // 2️⃣ Validar que client_id sea UUID
            UUID clientId;
            try {
                clientId = UUID.fromString(tokenRequest.getClientId());
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            // 3️⃣ Validar JWT firmado por el cliente
            String scopes = validationService.validateClientJwt(clientJwt, clientId);
            if (scopes.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            // 4️⃣ Generar token del servidor (ejemplo simple, puedes usar tu servicio JWT del servidor)
            TokenResponse response = new TokenResponse();
            response.setAccessToken(generateService.generateSignedJwt(tokenRequest.getClientId(), scopes, 3L));
            response.setTokenType("Bearer");
            response.setExpiresIn(300);
            response.scope(scopes);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
