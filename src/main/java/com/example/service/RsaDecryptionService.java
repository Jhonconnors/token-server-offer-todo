package com.example.service;

import com.example.config.SecurityCredentialsConfig;
import com.example.exception.InvalidJwtException;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

@Service
public class RsaDecryptionService {

    private final PrivateKey privateKey;

    public RsaDecryptionService(SecurityCredentialsConfig config) {
        this.privateKey = loadPrivateKey(config.getPrivateKey());
    }

    private PrivateKey loadPrivateKey(String pem) {
        try {
            String clean = pem
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s", "");

            byte[] pkcs8 = Base64.getDecoder().decode(clean);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(new PKCS8EncodedKeySpec(pkcs8));

        } catch (Exception e) {
            throw new InvalidJwtException("Invalid RSA private key :" + e.getMessage());
        }
    }

    public String decrypt(String encryptedBase64) {
        try {
            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedBase64);

            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            byte[] decrypted = cipher.doFinal(encryptedBytes);
            return new String(decrypted, StandardCharsets.UTF_8);

        } catch (Exception e) {
            throw new InvalidJwtException("RSA decryption failed :" + e);
        }
    }
}
