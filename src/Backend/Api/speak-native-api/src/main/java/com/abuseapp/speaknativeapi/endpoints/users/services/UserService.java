package com.abuseapp.speaknativeapi.endpoints.users.services;

import com.abuseapp.speaknativeapi.endpoints.users.models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.UUID;

public interface UserService extends UserDetailsService {
    User get(UUID id);
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
