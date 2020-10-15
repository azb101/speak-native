package com.abuseapp.speaknativeappapijava.endpoints.auth;

import com.abuseapp.speaknativeappapijava.endpoints.auth.exceptions.ValidationError;
import com.abuseapp.speaknativeappapijava.endpoints.auth.models.*;
import com.abuseapp.speaknativeappapijava.endpoints.auth.services.AuthService;
import com.abuseapp.speaknativeappapijava.endpoints.users.repositories.UserRepository;
import com.abuseapp.speaknativeappapijava.endpoints.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public AuthController(AuthService authService,
                          UserService userService,
                          UserRepository userRepository) {
        this.authService = authService;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto) throws Exception {
        return authService.login(loginRequestDto);
    }

    @PostMapping("/register")
    public RegisterResponseDto register(@RequestBody RegisterRequestDto registerRequestDto) throws ValidationError {
        return authService.register(registerRequestDto);
    }

    @PostMapping("/changePassword")
    public void changePassword(@RequestBody ChangePasswordRequestDto changePasswordRequestDto) throws Exception {
        authService.changePassword(changePasswordRequestDto);
    }
}
