package com.abuseapp.speaknativeappapijava.endpoints.auth.models;

import com.abuseapp.speaknativeappapijava.endpoints.users.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class LoginResponseDto {
    @NotNull
    private User user;

    @NotNull
    @NotEmpty
    private String token;
}
