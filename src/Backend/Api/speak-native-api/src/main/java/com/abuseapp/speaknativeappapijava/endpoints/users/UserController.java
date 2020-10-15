package com.abuseapp.speaknativeappapijava.endpoints.users;

import com.abuseapp.speaknativeappapijava.endpoints.users.models.User;
import com.abuseapp.speaknativeappapijava.endpoints.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public User Get(@PathVariable("id") UUID id) {
        return this.userService.get(id);
    }
}
