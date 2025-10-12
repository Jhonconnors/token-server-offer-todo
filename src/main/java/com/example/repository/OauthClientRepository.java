package com.example.repository;


import com.example.entity.OauthClient;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface OauthClientRepository extends JpaRepository<OauthClient, UUID> {
    Optional<OauthClient> findByClientId(UUID clientId);
}