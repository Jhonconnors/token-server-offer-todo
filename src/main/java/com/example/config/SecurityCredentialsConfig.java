package com.example.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "security.credentials")
@Getter
@Setter
public class SecurityCredentialsConfig {
    private String privateKey;
    private String clientId;
}