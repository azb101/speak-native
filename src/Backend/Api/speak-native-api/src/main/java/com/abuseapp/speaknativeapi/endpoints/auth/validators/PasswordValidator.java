package com.abuseapp.speaknativeapi.endpoints.auth.validators;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PasswordValidator {
    List<String> Validate(String password);
}
