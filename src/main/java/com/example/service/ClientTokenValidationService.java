package com.example.service;

import com.example.entity.OauthClient;
import com.example.load.OauthClientContext;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.UUID;

@Service
public class ClientTokenValidationService {

    private final OauthClientContext context;

    public ClientTokenValidationService(OauthClientContext context) {
        this.context = context;
    }

    public String validateClientJwt(String jwtToken, UUID clientId) throws Exception {
        OauthClient client = context.getOauthClientMap().get(clientId.toString());
        if (client == null)
            throw new IllegalArgumentException("Client not found");

        // Limpiar formato PEM y decodificar
        String publicKeyPem = client.getPublicKey()
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s+", "");

        byte[] keyBytes = Base64.getDecoder().decode(publicKeyPem);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(spec);

        // Validar JWT
        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(jwtToken);

        // Opcional: validar que el sub sea igual al clientId
        boolean isClientInSub = claims.getBody().getSubject().equals(clientId.toString());
        if (!isClientInSub)
            throw new IllegalArgumentException("Client not found");

        return String.join(" ", client.getScopes());
    }

    public String validateClientIdOnly(UUID clientId) {

        OauthClient client = context.getOauthClientMap().get(clientId.toString());
        if (client == null) {
            throw new IllegalArgumentException("Client not found");
        }

        // Retornar scopes asociados
        return String.join(" ", client.getScopes());
    }
}