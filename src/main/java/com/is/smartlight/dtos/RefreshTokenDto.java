package com.is.smartlight.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenDto {

    @NotNull
    @Size(min = 2, max = 10)
    private String client_id;

    @NotNull
    private String client_secret;

    @NotNull
    private String refresh_token;

    @NotNull
    private String grantType;

}
