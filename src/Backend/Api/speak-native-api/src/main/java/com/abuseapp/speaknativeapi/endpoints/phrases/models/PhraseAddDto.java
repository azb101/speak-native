package com.abuseapp.speaknativeapi.endpoints.phrases.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
public class PhraseAddDto {
    @NotNull
    private UUID userId;

    @NotNull
    @NotEmpty
    private String targetLang;

    @NotNull
    @NotEmpty
    private String referenceLang;
}
