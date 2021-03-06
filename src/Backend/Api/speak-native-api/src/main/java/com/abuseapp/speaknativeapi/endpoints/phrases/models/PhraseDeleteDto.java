package com.abuseapp.speaknativeapi.endpoints.phrases.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class PhraseDeleteDto {
    @NotNull
    @NotEmpty
    private UUID userId;

    @NotNull
    @NotEmpty
    private List<UUID> phraseIds;
}
