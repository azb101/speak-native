package com.abuseapp.speaknativeappapijava.infrastructure.security;

import com.abuseapp.speaknativeappapijava.endpoints.users.services.UserService;
import com.abuseapp.speaknativeappapijava.infrastructure.tokens.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static com.abuseapp.speaknativeappapijava.helpers.StringHelpers.isBlank;
import static com.abuseapp.speaknativeappapijava.infrastructure.tokens.TokenConstants.TOKEN_HEADER;
import static com.abuseapp.speaknativeappapijava.infrastructure.tokens.TokenConstants.TOKEN_PREFIX;

class JwtAuthorizationFilter extends OncePerRequestFilter {


    private final AuthenticationManager auth;
    private final Environment appConfigs;
    private final TokenService tokenService;
    private final UserService userService;

    @Autowired
    protected JwtAuthorizationFilter(AuthenticationManager authenticationManager,
                                     Environment appConfigs,
                                     TokenService tokenService,
                                     UserService userService) {

        this.auth = authenticationManager;
        this.appConfigs = appConfigs;
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        try {
            if (shouldNotSkipTokenCheck(request.getRequestURI())) {
                var header = request.getHeader(TOKEN_HEADER);
                if (doesNotHaveToken(header))
                    throw new InvalidTokenException("Token header is missing");

                var token = header.substring(TOKEN_PREFIX.length());
                if(isBlank(token))
                    throw new InvalidTokenException("Token is missing");

                var id = tokenService.getId(token);
                if(id == null)
                    throw new InvalidTokenException("Invalid token data");

                if(SecurityContextHolder.getContext().getAuthentication() == null) {
                    var user = userService.get(id);
                    tokenService.validateToken(token, user);

                    var authToken = new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        chain.doFilter(request, response);
    }

    private boolean shouldNotSkipTokenCheck(String uri) {
        var permissibleUris = List.of(appConfigs.getProperty("auth.free.uris").split(";"));
        return !permissibleUris.contains(uri);
    }

    private boolean doesNotHaveToken(String header) {
        return isBlank(header) || !header.startsWith(TOKEN_PREFIX);
    }
}
