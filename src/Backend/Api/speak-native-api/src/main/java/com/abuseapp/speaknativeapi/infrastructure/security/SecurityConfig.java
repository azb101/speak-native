package com.abuseapp.speaknativeapi.infrastructure.security;

import com.abuseapp.speaknativeapi.endpoints.users.services.UserService;
import com.abuseapp.speaknativeapi.infrastructure.tokens.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.security.SecureRandom;
import java.util.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final Environment appConfigs;
    private final UserService userService;
    private final TokenService tokenService;

    @Autowired
    public SecurityConfig(Environment appConfigs,
                          UserService userService,
                          TokenService tokenService) {
        this.appConfigs = appConfigs;
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .authorizeRequests()
                    .antMatchers(HttpMethod.POST, getPermissibleUris()).permitAll()
                    .anyRequest().authenticated()
                    .and()
                .addFilterBefore(new JwtAuthorizationFilter(authenticationManager(), appConfigs, tokenService, userService),
                        UsernamePasswordAuthenticationFilter.class)
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    protected CorsConfigurationSource corsConfigurationSource()
    {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(getAllowedCorsHosts());
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type", "Charset"));

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(
                Integer.valueOf(appConfigs.getProperty("bcrypt.hashing.strength")),
                new SecureRandom());
    }

    private String[] getPermissibleUris() {
        return appConfigs.getProperty("auth.free.uris").split(";");
    }

    private List<String> getAllowedCorsHosts() {
        return Collections.unmodifiableList(Arrays.asList(appConfigs.getProperty("cors.allowedHosts").split(",")));
    }

}
