package com.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "oauth_clients")
@Getter
@Setter
public class OauthClient {

    @Id
    @Column(name = "client_id")
    private UUID clientId;

    @Column(name = "client_name", nullable = false)
    private String clientName;

    @Column(name = "public_key", columnDefinition = "text", nullable = false)
    private String publicKey;

    @Column(name = "scopes", nullable = false)
    private String[] scopes;
}
