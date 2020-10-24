package com.abuseapp.speaknativeapi.infrastructure.tokens;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {

    private final long jwtTokenValidity;
    private final String secret;

    public JwtTokenUtil(Environment appConfigs)
    {
        jwtTokenValidity = Integer.valueOf(appConfigs.getProperty("jwt.config.expiration.days")) * 60 * 60 * 1000;
        secret = appConfigs.getProperty("jwt.config.secret");
    }

    public String generateToken(Map<String, Object> claims, String subject)
    {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtTokenValidity))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public boolean isTokenExpired(String token)
    {
        var expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(new Date(System.currentTimeMillis()));
    }

    public UUID getId(String token)
    {
        return UUID.fromString(getClaimFromToken(token, Claims::getSubject));
    }

    public String getEmail(String token) {
        var claims = getAllClaimsFromToken(token);
        return claims.get("email", String.class);
    }

    private Date getExpirationDateFromToken(String token)
    {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver)
    {
        var claims = getAllClaimsFromToken(token);

        return claimResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token)
    {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }


}
