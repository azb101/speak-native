package com.abuseapp.speaknativeappapijava.endpoints.users.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NonNull;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Data
@Entity
@ToString
@Table(catalog = "speaknativedb", schema = "[dbo]" , name = "[appusers]")
public final class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @NotNull
    @NotEmpty
    @Email
    @Column(name = "email")
    private String email;

    @ToString.Exclude
    @NonNull
    @NotEmpty
    @JsonIgnore
    @Column(name = "password")
    private String password;

    public User() {
    }

    public static User fromEmail(String email) {
        var user = new User();
        user.setEmail(email);
        return user;
    }

    public static User fromId(UUID userId) {
        var user = new User();
        user.setId(userId);
        return user;
    }
}
