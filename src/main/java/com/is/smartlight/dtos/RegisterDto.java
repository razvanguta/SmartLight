package com.is.smartlight.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {

    private String email;

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    private String phone;

    private String role;
}
