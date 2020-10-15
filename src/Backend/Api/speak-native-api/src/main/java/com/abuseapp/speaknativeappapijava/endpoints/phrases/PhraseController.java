package com.abuseapp.speaknativeappapijava.endpoints.phrases;

import com.abuseapp.speaknativeappapijava.endpoints.phrases.models.*;
import com.abuseapp.speaknativeappapijava.endpoints.phrases.services.PhraseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/phrases")
public class PhraseController {

    private final PhraseService phraseService;

    @Autowired
    public PhraseController(PhraseService phraseService) {
        this.phraseService = phraseService;
    }

    @GetMapping("/{userId}")
    public List<Phrase> getRandom(@PathVariable("userId")UUID userId) {
        return phraseService.getRandom(userId);
    }

    @GetMapping
    public List<Phrase> getAll(@RequestParam(value = "userId") UUID userId,
                               @RequestParam(value = "page", required = false) Integer page,
                               @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return phraseService.getAll(userId, page, pageSize);
    }

    @PostMapping
    public Phrase add(@RequestBody PhraseAddDto phraseAddDto) {
        return phraseService.addOne(phraseAddDto);
    }

    @PutMapping
    public Phrase update(@RequestBody PhraseUpdateDto phraseUpdateDto){
        return phraseService.updateOne(phraseUpdateDto);
    }

    @DeleteMapping
    public void delete(@RequestBody PhraseDeleteDto phraseDeleteDto) {
        phraseService.delete(phraseDeleteDto);
    }

    @PutMapping("/increment")
    public void incrementFailingRates(@RequestBody PhraseIncrementFailingRatesDto phraseIncrementFailingRatesDto) {
        phraseService.incrementFailingRates(phraseIncrementFailingRatesDto);
    }
}
