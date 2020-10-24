package com.abuseapp.speaknativeapi.infrastructure.tokens;

import com.abuseapp.speaknativeapi.endpoints.users.models.User;
import com.abuseapp.speaknativeapi.infrastructure.security.InvalidTokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.UUID;

@Service
public class JwtTokenService implements TokenService {

    private final Environment appConfigs;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public JwtTokenService(Environment appConfigs,
                           JwtTokenUtil jwtTokenUtil)
    {
        this.appConfigs = appConfigs;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public String generateToken(@NotNull User user)
    {
        var claims = new HashMap<String, Object>();
        claims.put("email", user.getEmail());
        claims.put("id", user.getId());
        return jwtTokenUtil.generateToken(claims, user.getId().toString());
    }

    @Override
    public void validateToken(@NotNull @NotEmpty String token, @NotNull User user)
            throws InvalidTokenException
    {
        var email = jwtTokenUtil.getEmail(token);
        var expired = jwtTokenUtil.isTokenExpired(token);
        var id = jwtTokenUtil.getId(token);

        if(expired)
            throw new InvalidTokenException(appConfigs.getProperty("jwt.token.expired.message"));

        if(user == null)
            throw new InvalidTokenException(appConfigs.getProperty("jwt.token.account.disabled"));

        if (!id.equals(user.getId()))
            throw new InvalidTokenException(appConfigs.getProperty("jwt.token.invalid.token.data"));

        if (!email.equals(user.getEmail()))
            throw new InvalidTokenException(appConfigs.getProperty("jwt.token.invalid.token.data"));
    }

    @Override
    public String getEmail(@NotNull @NotEmpty String token) {
        return jwtTokenUtil.getEmail(token);
    }

    @Override
    public UUID getId(@NotNull @NotEmpty String token) {
        return jwtTokenUtil.getId(token);
    }
}

