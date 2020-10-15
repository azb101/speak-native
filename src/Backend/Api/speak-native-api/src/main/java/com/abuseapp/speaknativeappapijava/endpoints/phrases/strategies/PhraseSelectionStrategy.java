package com.abuseapp.speaknativeappapijava.endpoints.phrases.strategies;

import com.abuseapp.speaknativeappapijava.endpoints.phrases.models.Phrase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PhraseSelectionStrategy {
    List<Phrase> apply(List<Phrase> phrases);
}
