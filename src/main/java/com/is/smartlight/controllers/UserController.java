package com.is.smartlight.controllers;

import com.is.smartlight.dtos.RegisterDto;
import com.is.smartlight.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.is.smartlight.utility.HttpStatusUtility.successResponse;

@RestController
@Tag(name = "User Controller", description = "Set of endpoints for Creating, Retrieving, Updating and Deleting of users.")
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Register users.")
    @PostMapping("/register-user")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDto registerDto) {
        userService.registerAdmin(registerDto);
        return successResponse();
    }

    @Operation(summary = "Register admins.")
    @PostMapping("/register-admin")
    public ResponseEntity<?> registerAdmin(@RequestBody RegisterDto registerDto) {
        userService.registerAdmin(registerDto);
        return successResponse();
    }
}
