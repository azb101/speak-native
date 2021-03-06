package com.abuseapp.speaknativeapi.infrastructure.tokens;

import com.abuseapp.speaknativeapi.endpoints.users.models.User;
import com.abuseapp.speaknativeapi.infrastructure.security.InvalidTokenException;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Component
public interface TokenService {
    String generateToken(@NotNull User user);
    void validateToken(@NotNull @NotEmpty String token, @NotNull User user) throws InvalidTokenException;
    String getEmail(@NotNull @NotEmpty String token);
    UUID getId(@NotNull @NotEmpty String token);
}
