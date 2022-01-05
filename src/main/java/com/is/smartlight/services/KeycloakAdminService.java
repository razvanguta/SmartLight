package com.is.smartlight.services;

import com.is.smartlight.dtos.ChangePasswordDto;
import com.is.smartlight.utility.KeycloakUtility;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.ws.rs.core.Response;
import java.util.Collections;

import static org.keycloak.admin.client.CreatedResponseUtil.getCreatedId;

@Service
public class KeycloakAdminService {

    @Value("${keycloak.realm}")
    private String keycloakRealm;

    private final Keycloak keycloak;
    private RealmResource realm;

    @Autowired
    public KeycloakAdminService(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    @PostConstruct
    public void initRealmResource() {
        this.realm = this.keycloak.realm(keycloakRealm);
    }

    public void registerUser(Long userId, String password, String role) {
        UserRepresentation keycloakUser = new UserRepresentation();
        keycloakUser.setEnabled(true);
        keycloakUser.setUsername(userId.toString());


        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();

        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(password);
        credentialRepresentation.setTemporary(false);

        keycloakUser.setCredentials(Collections.singletonList(credentialRepresentation));

        // Add the user to the Keycloak Realm
        Response response = realm.users().create(keycloakUser);
        String keycloakUserId = getCreatedId(response);

        UserResource userResource = realm.users().get(keycloakUserId);
        RoleRepresentation roleRepresentation = realm.roles().get(role).toRepresentation();
        userResource.roles().realmLevel().add(Collections.singletonList(roleRepresentation));

    }

    public void deleteUser(Long userId) {
        Keycloak adminClient = KeycloakUtility.getAdminClient();
        RealmResource realmResource = adminClient.realm(keycloakRealm);

        UserRepresentation userRepresentation = realmResource.users().search(userId.toString()).get(0);

        realmResource.users().delete(userRepresentation.getId());
    }

    public void changePassword(ChangePasswordDto changePasswordDto) {
        Keycloak adminClient = KeycloakUtility.getAdminClient();
        RealmResource realmResource = adminClient.realm(keycloakRealm);

        UserRepresentation userRepresentation = realmResource.users().search(changePasswordDto.getUserId().toString()).get(0);

        CredentialRepresentation newCredential = new CredentialRepresentation();
        newCredential.setType(CredentialRepresentation.PASSWORD);
        newCredential.setValue(changePasswordDto.getNewPassword());
        newCredential.setTemporary(false);
        userRepresentation.setCredentials(Collections.singletonList(newCredential));

        realmResource.users().get(userRepresentation.getId()).update(userRepresentation);
    }

}