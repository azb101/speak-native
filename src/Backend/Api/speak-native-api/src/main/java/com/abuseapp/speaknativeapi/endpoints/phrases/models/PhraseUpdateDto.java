package com.abuseapp.speaknativeapi.endpoints.phrases.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
public class PhraseUpdateDto {
    @NotNull
    @NotEmpty
    private UUID phraseId;

    @NotNull
    @NotEmpty
    private String targetLang;

    @NotNull
    @NotEmpty
    private String referenceLang;
}
