package com.abuseapp.speaknativeapi.endpoints.phrases.strategies;

import com.abuseapp.speaknativeapi.endpoints.phrases.models.Phrase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.Collections.swap;

@Service
public class RandomPhaseSelectionStrategy implements PhraseSelectionStrategy {

    private final int pageSize;

    @Autowired
    public RandomPhaseSelectionStrategy(Environment appConfigs) {
        this.pageSize = Integer.valueOf(appConfigs.getProperty("phrase.get.random.max.page.size"));
    }

    @Override
    public List<Phrase> apply(List<Phrase> phrases) {
        int n = phrases.size();
        var random = ThreadLocalRandom.current();
        var res = new ArrayList<Phrase>();

        for(int i=Math.min(pageSize, n); i > 0; i--){
            var nextId = random.nextInt(i);

            var nextItem = phrases.get(nextId);
            res.add(nextItem);

            swap(phrases, nextId, i-1);
        }

        return res;
    }
}
