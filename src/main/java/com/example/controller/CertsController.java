package com.example.controller;

import com.example.contract.api.CertsApi;
import com.example.contract.model.JwkSet;
import com.example.contract.model.JwkSetKeysInner;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Collections;

@Controller
@Slf4j
public class CertsController implements CertsApi {

    @Value("${security.credentials.public-key}")
    private String publicKeyPem;

    private JwkSet jwkSet;

    @PostConstruct
    public void init() {
        try {
            // Convert PEM to RSAPublicKey
            String pem = publicKeyPem
                    .replaceAll("-----BEGIN PUBLIC KEY-----", "")
                    .replaceAll("-----END PUBLIC KEY-----", "")
                    .replaceAll("\\s+", "");
            byte[] keyBytes = Base64.getDecoder().decode(pem);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            RSAPublicKey rsaPubKey = (RSAPublicKey) kf.generatePublic(spec);

            // Generar JWK
            JwkSetKeysInner jwkSetKeysInner = new JwkSetKeysInner();
            jwkSetKeysInner.setKty("RSA");
            jwkSetKeysInner.setUse("sig");
            jwkSetKeysInner.setAlg("RS512");
            jwkSetKeysInner.setKid("rsa-key-1");
            jwkSetKeysInner.setN(Base64.getUrlEncoder().withoutPadding().encodeToString(rsaPubKey.getModulus().toByteArray()));
            jwkSetKeysInner.setE(Base64.getUrlEncoder().withoutPadding().encodeToString(rsaPubKey.getPublicExponent().toByteArray()));

            jwkSet = new JwkSet();
            jwkSet.setKeys(Collections.singletonList(jwkSetKeysInner));
        } catch (Exception e) {
            log.error("Error initializing JWK", e);
        }
    }

    @Override
    public ResponseEntity<JwkSet> certsGet() {
        if (jwkSet == null) {
            throw new SecurityException();
        }
        return ResponseEntity.ok(jwkSet);
    }
}