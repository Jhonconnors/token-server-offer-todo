package com.example.load;

import com.example.entity.OauthClient;
import com.example.repository.OauthClientRepository;
import lombok.Getter;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;


import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class OauthClientContext {

    @Getter
    private ConcurrentHashMap<String, OauthClient> oauthClientMap = new ConcurrentHashMap<>();

    private final OauthClientRepository repository;

    public OauthClientContext(OauthClientRepository repository) {
        this.repository = repository;
        loadTableList();
    }

    @Scheduled(fixedDelay = 1000 * 60) // cada minuto
    private void loadTableList() {
        List<OauthClient> clients = repository.findAll();
        ConcurrentHashMap<String, OauthClient> map = new ConcurrentHashMap<>();
        for (OauthClient client : clients) {
            map.put(client.getClientId().toString(), client);
        }
        oauthClientMap = map;
    }
}
