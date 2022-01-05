package com.is.smartlight.services;

import com.is.smartlight.config.AuthClient;
import com.is.smartlight.dtos.LoginDto;
import com.is.smartlight.dtos.RefreshTokenDto;
import com.is.smartlight.dtos.TokenDto;
import com.is.smartlight.models.User;
import com.is.smartlight.repositories.UserRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.ws.rs.NotFoundException;
import java.util.Optional;

@Service
public class AuthService {

    private final AuthClient authClient;
    private final UserRepository userRepository;

    @Autowired
    AuthService(AuthClient authClient, UserRepository userRepository) {
        this.authClient = authClient;
        this.userRepository = userRepository;
    }

    @SneakyThrows
    public TokenDto login(LoginDto loginDto) {

        Optional<User> inAppUser = userRepository.findByEmail(loginDto.getEmail());
        if (inAppUser.isEmpty()) {
            throw new NotFoundException("The user doesn't exist!");
        }

        // Set the request body
        MultiValueMap<String, String> loginCredentials = new LinkedMultiValueMap<>();
        loginCredentials.add("client_id", loginDto.getClientId());
        loginCredentials.add("client_secret", loginDto.getClientSecret());
        loginCredentials.add("username", inAppUser.get().getId().toString());
        loginCredentials.add("password", loginDto.getPassword());
        loginCredentials.add("grant_type", loginDto.getGrantType());
        // Keycloak login (will return an Access Token)
        TokenDto token = authClient.login(loginCredentials);
        return token;

    }

    @SneakyThrows
    public TokenDto refresh(RefreshTokenDto refreshTokenDto) {

        // Set the request body
        MultiValueMap<String, String> refreshCredentials = new LinkedMultiValueMap<>();
        refreshCredentials.add("client_id", refreshTokenDto.getClient_id());
        refreshCredentials.add("client_secret", refreshTokenDto.getClient_secret());
        refreshCredentials.add("refresh_token", refreshTokenDto.getRefresh_token());
        refreshCredentials.add("grant_type", refreshTokenDto.getGrantType());

        TokenDto token = authClient.refresh(refreshCredentials);
        return token;
    }

}
