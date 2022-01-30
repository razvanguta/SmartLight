package com.is.smartlight.services;

import com.is.smartlight.dtos.RegisterDto;
import com.is.smartlight.models.User;
import com.is.smartlight.repositories.UserRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.BadRequestException;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final KeycloakAdminService keycloakAdminService;

    @Autowired
    public UserService(UserRepository userRepository, KeycloakAdminService keycloakAdminService) {
        this.userRepository = userRepository;
        this.keycloakAdminService = keycloakAdminService;
    }

    @SneakyThrows
    public void registerUser(RegisterDto registerDto) {
        if (userRepository.findByEmail(registerDto.getEmail()).isPresent()) {
            throw new BadRequestException("User with email " + registerDto.getEmail() + " already exists!");
        }
        User user = User.builder()
                .firstName(registerDto.getFirstName())
                .lastName(registerDto.getLastName())
                .email(registerDto.getEmail())
                .username(registerDto.getUsername())
                .birthDate(registerDto.getBirthDate())
                .role("user")
                .build();
        user = userRepository.save(user);
        keycloakAdminService.registerUser(user.getId(), registerDto.getPassword(), "ROLE_USER");
    }

    @SneakyThrows
    public void registerAdmin(RegisterDto registerDto) {
        if (userRepository.findByEmail(registerDto.getEmail()).isPresent()) {
            throw new BadRequestException("User with email " + registerDto.getEmail() + " already exists!");
        }
        User user = User.builder()
                .firstName(registerDto.getFirstName())
                .lastName(registerDto.getLastName())
                .email(registerDto.getEmail())
                .username(registerDto.getUsername())
                .birthDate(registerDto.getBirthDate())
                .role("admin")
                .build();
        user = userRepository.save(user);
        keycloakAdminService.registerUser(user.getId(), registerDto.getPassword(), "ROLE_ADMIN");
    }

}
