package com.abuseapp.speaknativeapi.endpoints.auth.services;

import com.abuseapp.speaknativeapi.endpoints.auth.exceptions.ValidationError;
import com.abuseapp.speaknativeapi.endpoints.auth.models.*;
import com.abuseapp.speaknativeapi.endpoints.auth.validators.PasswordValidator;
import com.abuseapp.speaknativeapi.endpoints.users.models.User;
import com.abuseapp.speaknativeapi.endpoints.users.repositories.UserRepository;
import com.abuseapp.speaknativeapi.infrastructure.tokens.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.validation.constraints.NotNull;

@Service
public class AuthServiceImpl implements AuthService {

    private final Environment appConfigs;
    private final AuthenticationManager authManager;
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final PasswordValidator passwordValidator;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(Environment appConfigs,
                           AuthenticationManager authManager,
                           UserRepository userRepository,
                           TokenService tokenService,
                           PasswordValidator passwordValidator,
                           PasswordEncoder passwordEncoder) {
        this.appConfigs = appConfigs;
        this.authManager = authManager;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.passwordValidator = passwordValidator;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public LoginResponseDto login(@NotNull LoginRequestDto loginRequestDto)
            throws Exception
    {
        authenticate(loginRequestDto.getEmail(),loginRequestDto.getPassword());

        var user = userRepository.findByEmail(loginRequestDto.getEmail());
        var token = tokenService.generateToken(user);

        return new LoginResponseDto(user, token);
    }

    private void authenticate(String email, String password) throws Exception {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        email,
                        password));
    }

    @Override
    @Transactional
    public RegisterResponseDto register(@NotNull RegisterRequestDto registerRequestDto) throws ValidationError {

        AssertPasswordIsProper(registerRequestDto.getPassword());
        AssertEmailIsUnique(registerRequestDto.getEmail());

        var passwordHash = passwordEncoder.encode(registerRequestDto.getPassword());

        var user = userRepository.save(createUserFromDto(registerRequestDto, passwordHash));

        return new RegisterResponseDto(user);
    }

    @Override
    @Transactional
    public void changePassword(@NotNull ChangePasswordRequestDto changePasswordRequestDto) throws Exception {
        authenticate(changePasswordRequestDto.getEmail(),
                changePasswordRequestDto.getOldPassword());

        AssertPasswordsAreEqual(changePasswordRequestDto.getNewPassword(), changePasswordRequestDto.getNewPasswordVerify());
        AssertOldAndNewPasswordsAreNotEqual(changePasswordRequestDto.getOldPassword(), changePasswordRequestDto.getNewPassword());
        AssertPasswordIsProper(changePasswordRequestDto.getNewPassword());

        var newPasswordHash = passwordEncoder.encode(changePasswordRequestDto.getNewPassword());
        var user = userRepository.findByEmail(changePasswordRequestDto.getEmail());
        user.setPassword(newPasswordHash);

        userRepository.save(user);
    }

    private void AssertPasswordsAreEqual(String newPassword, String newPasswordVerify) throws ValidationError {
        if(!newPassword.equals(newPasswordVerify))
            throw new ValidationError(appConfigs.getProperty("validation.password.change.passwords.not.equal"));
    }

    private void AssertOldAndNewPasswordsAreNotEqual(String oldPassword, String newPassword) throws ValidationError {
        if(oldPassword.equals(newPassword))
            throw new ValidationError(appConfigs.getProperty("validation.password.change.passwords.old.equal.new"));
    }

    private void AssertEmailIsUnique(String email) throws ValidationError {
        final ExampleMatcher emailMatcher =
                ExampleMatcher
                        .matching()
                        .withMatcher("email", ExampleMatcher.GenericPropertyMatchers.ignoreCase());

        var example = Example.of(User.fromEmail(email), emailMatcher);

        if(userRepository.exists(example))
            throw new ValidationError(appConfigs.getProperty("validation.user.already.exists.message"));
    }

    private void AssertPasswordIsProper(String password) throws ValidationError {
        var passwordValidationResult = passwordValidator.Validate(password);
        if (passwordValidationResult != null && passwordValidationResult.size() != 0)
            throw ValidationError.fromMessages(passwordValidationResult);
    }

    private User createUserFromDto(RegisterRequestDto userRegisterDto, String hash) {
        var user = new User();
        user.setEmail(userRegisterDto.getEmail());
        user.setPassword(hash);
        return user;
    }
}
