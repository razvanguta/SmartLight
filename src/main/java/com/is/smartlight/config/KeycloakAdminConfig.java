package com.is.smartlight.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakAdminConfig {

    @Value("${keycloak.auth-server-url}")
    private String keycloakAuthServer;

    @Value("${admin-keycloak.username}")
    private String adminKeycloakUsername;

    @Value("${admin-keycloak.password}")
    private String adminKeycloakPassword;

    @Value("${admin-keycloak.realm}")
    private String adminKeycloakRealm;

    @Value("${admin-keycloak.client}")
    private String adminKeycloakClient;

    @Bean
    public Keycloak getKeycloak() {
        return KeycloakBuilder.builder()
                .serverUrl(keycloakAuthServer)
                .username(adminKeycloakUsername)
                .grantType(OAuth2Constants.PASSWORD)
                .password(adminKeycloakPassword)
                .realm(adminKeycloakRealm)
                .clientId(adminKeycloakClient)
                .build();
    }

}