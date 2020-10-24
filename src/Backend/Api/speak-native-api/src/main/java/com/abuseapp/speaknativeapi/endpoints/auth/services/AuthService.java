package com.abuseapp.speaknativeapi.endpoints.auth.services;

import com.abuseapp.speaknativeapi.endpoints.auth.exceptions.ValidationError;
import com.abuseapp.speaknativeapi.endpoints.auth.models.*;

public interface AuthService {
    LoginResponseDto login(LoginRequestDto loginRequestDto) throws Exception;
    RegisterResponseDto register(RegisterRequestDto registerRequestDto) throws ValidationError;
    void changePassword(ChangePasswordRequestDto changePasswordRequestDto) throws Exception;
}
