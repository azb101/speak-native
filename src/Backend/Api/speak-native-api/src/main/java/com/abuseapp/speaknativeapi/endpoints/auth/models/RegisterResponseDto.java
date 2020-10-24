package com.abuseapp.speaknativeapi.endpoints.auth.models;

import com.abuseapp.speaknativeapi.endpoints.users.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class RegisterResponseDto {
    @NotNull
    private User user;
}
