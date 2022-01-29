package com.is.smartlight.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class LoginDto {

    @NotNull(message = "Invalid email")
    @Email(message = "Invalid email")
    @Size(min = 3, max = 40, message = "Invalid email")
    private String email;

    @NotNull(message = "Invalid password")
    @Size(min = 5, max = 30, message = "Invalid password")
    private String password;

    private String grantType;

}
