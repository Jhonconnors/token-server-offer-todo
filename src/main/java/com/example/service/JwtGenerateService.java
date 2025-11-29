package com.example.service;

import com.example.config.SecurityCredentialsConfig;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtGenerateService {

    private final SecurityCredentialsConfig config;

    public JwtGenerateService(SecurityCredentialsConfig config) {
        this.config = config;
    }

    public String generateSignedJwt(String clientId, String scopes) throws Exception {
        // Convertir PEM a PrivateKey
        String privateKeyPem = config.getPrivateKey()
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s+", "");

        byte[] keyBytes = Base64.getDecoder().decode(privateKeyPem);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        PrivateKey privateKey = KeyFactory.getInstance("RSA").generatePrivate(spec);

        // Crear JWT firmado
        return Jwts.builder()
                .setSubject(clientId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3 * 60 * 1000)) // 3 min
                .claim("scope", scopes)
                .claim("no_mirar", "wr9UZSBhdHJldmVzIGEgdXNhciBtaXMgaGVjaGl6b3MgZW4gbWkgY29udHJhLCBQb3R0ZXI/Cg==")
                .signWith(privateKey, SignatureAlgorithm.RS512)
                .compact();
    }

    public byte[] rsaOaepDecrypt(byte[] ciphertext) throws Exception {
        // load PK
        PrivateKey privateKey = loadPrivateKeyPem();
        // OAEP with SHA-512
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-512AndMGF1Padding");
        // Some JVMs support the above; if not, use OAEPParameterSpec explicitly:
        OAEPParameterSpec oaepParams = new OAEPParameterSpec(
                "SHA-512",
                "MGF1",
                new MGF1ParameterSpec("SHA-512"),
                PSource.PSpecified.DEFAULT
        );
        cipher.init(Cipher.DECRYPT_MODE, privateKey, oaepParams);
        return cipher.doFinal(ciphertext);
    }

    public PrivateKey loadPrivateKeyPem() throws Exception {
        String base64 = config.getPrivateKey()
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s+", "");
        byte[] keyBytes = Base64.getDecoder().decode(base64);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }
}