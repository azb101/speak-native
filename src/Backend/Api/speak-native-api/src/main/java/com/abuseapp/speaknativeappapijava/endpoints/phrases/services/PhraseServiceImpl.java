package com.abuseapp.speaknativeappapijava.endpoints.phrases.services;

import com.abuseapp.speaknativeappapijava.endpoints.phrases.models.*;
import com.abuseapp.speaknativeappapijava.endpoints.phrases.repositories.PhraseRepository;
import com.abuseapp.speaknativeappapijava.endpoints.phrases.strategies.PhraseSelectionStrategy;
import com.abuseapp.speaknativeappapijava.endpoints.users.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.abuseapp.speaknativeappapijava.helpers.StringHelpers.isBlank;

@Service
public class PhraseServiceImpl implements PhraseService{

    private final int maxPageSize;
    private final PhraseRepository phraseRepository;
    private final PhraseSelectionStrategy phraseSelectionStrategy;

    @Autowired
    public PhraseServiceImpl(Environment appConfigs,
                             PhraseRepository phraseRepository,
                             PhraseSelectionStrategy phraseSelectionStrategy) {
        this.maxPageSize = Integer.valueOf(appConfigs.getProperty("phrase.get.all.max.page.size"));
        this.phraseRepository = phraseRepository;
        this.phraseSelectionStrategy = phraseSelectionStrategy;
    }

    @Override
    public List<Phrase> getAll(@NotNull UUID userId, Integer page, Integer pageSize) {

        if(page == null)
            page = 0;

        if(pageSize == null || pageSize == 0)
            pageSize = maxPageSize;

        return phraseRepository.findAllByUserId(userId,
                PageRequest.of(page, pageSize)).getContent();
    }

    @Override
    public List<Phrase> getRandom(UUID userId) {
        var pageItems = phraseRepository.findAllByUserId(userId,
                PageRequest.of(0,
                        maxPageSize,
                        Sort.by(Sort.Direction.DESC, "createdAt")));

        var allItems = new ArrayList<Phrase>();
        for(var pageItem : pageItems){
            allItems.add(pageItem);
        };

        return phraseSelectionStrategy.apply(allItems);
    }

    @Override
    @Transactional
    public Phrase addOne(PhraseAddDto phraseAddDto) {
        var phrase = new Phrase();
        phrase.setReferenceLang(phraseAddDto.getReferenceLang());
        phrase.setTargetLang(phraseAddDto.getTargetLang());
        phrase.setUser(User.fromId(phraseAddDto.getUserId()));
        phrase.setFailingRate(0);
        phrase.setCreatedAt(LocalDateTime.now());

        return phraseRepository.save(phrase);
    }

    @Override
    @Transactional
    public Phrase updateOne(PhraseUpdateDto phraseUpdateDto) {
        var phrase = phraseRepository.getOne(phraseUpdateDto.getPhraseId());
        if(!isBlank(phraseUpdateDto.getReferenceLang()))
            phrase.setReferenceLang(phraseUpdateDto.getReferenceLang());

        if(!isBlank(phraseUpdateDto.getTargetLang()))
            phrase.setTargetLang(phraseUpdateDto.getTargetLang());

        return phraseRepository.save(phrase);
    }

    @Override
    @Transactional
    public void delete(PhraseDeleteDto phraseDeleteDto) {
        phraseRepository.deleteByUserIdAndIds(phraseDeleteDto.getUserId(), phraseDeleteDto.getPhraseIds());
    }

    @Override
    @Transactional
    public void incrementFailingRates(PhraseIncrementFailingRatesDto incrementFailingRatesDto) {
        var phrases = phraseRepository.findAllById(incrementFailingRatesDto.getPhraseIds());
        phrases.forEach((p) -> {
            p.setFailingRate(p.getFailingRate() + 1);
        });
        phraseRepository.saveAll(phrases);
    }
}
