package com.abuseapp.speaknativeapi.endpoints.phrases.services;

import com.abuseapp.speaknativeapi.endpoints.phrases.models.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface PhraseService {
    List<Phrase> getAll(UUID userId, Integer page, Integer pageSize);
    List<Phrase> getRandom(UUID userId);
    Phrase addOne(PhraseAddDto phraseAddDto);
    Phrase updateOne(PhraseUpdateDto phraseUpdateDto);
    void delete(PhraseDeleteDto phraseDeleteDto);
    void incrementFailingRates(PhraseIncrementFailingRatesDto updateFailinRateDto);
}
