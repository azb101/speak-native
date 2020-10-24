package com.abuseapp.speaknativeapi.endpoints.phrases.models;

import com.abuseapp.speaknativeapi.endpoints.users.models.User;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@ToString
@Entity
@Table(catalog = "speaknativedb", schema = "[dbo]" , name = "[phrases]")
public class Phrase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @NotNull
    @NotEmpty
    @Column(name = "referencelang")
    private String referenceLang;

    @NotNull
    @NotEmpty
    @Column(name = "targetlang")
    private String targetLang;

    @Column(name = "createdat")
    private LocalDateTime createdAt;

    @Column(name = "failingrate")
    private int failingRate;

    @NotNull
    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name="userid")
    private User user;
}
