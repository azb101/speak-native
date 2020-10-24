package com.abuseapp.speaknativeapi.endpoints.users.services;

import com.abuseapp.speaknativeapi.endpoints.users.models.User;
import com.abuseapp.speaknativeapi.endpoints.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static java.util.Collections.emptyList;

@Service
public class UserServiceImpl implements UserService {

    private final Environment appConfigs;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(Environment appConfigs,
                           UserRepository userRepository) {
        this.appConfigs = appConfigs;
        this.userRepository = userRepository;
    }

    @Override
    public User get(UUID id) {
        return userRepository.getOne(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(username);
        if(user == null)
            throw new UsernameNotFoundException(appConfigs.getProperty("validation.user.email.not.registered.message"));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), emptyList());
    }
}
