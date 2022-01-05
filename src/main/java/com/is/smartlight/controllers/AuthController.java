package com.is.smartlight.controllers;

import com.is.smartlight.dtos.LoginDto;
import com.is.smartlight.dtos.RefreshTokenDto;
import com.is.smartlight.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Auth Controller", description = "Set of endpoints for access and refresh tokens for user.")
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Logs user in and returns an access and refresh token")
    @PostMapping("/login")
    public ResponseEntity<?> login(LoginDto loginDto) {
        return new ResponseEntity<>(authService.login(loginDto), HttpStatus.OK);
    }

    @Operation(summary = "Receives a refresh token when the access token expired")
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(RefreshTokenDto refreshTokenDto) {
        return new ResponseEntity<>(authService.refresh(refreshTokenDto), HttpStatus.OK);
    }

}
