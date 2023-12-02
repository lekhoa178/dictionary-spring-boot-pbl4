package com.pbl4.monolingo.rest;

import com.pbl4.monolingo.entity.embeddable.LevelId;
import com.pbl4.monolingo.service.NLTKService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import simplenlg.framework.*;
import simplenlg.lexicon.*;
import simplenlg.realiser.english.*;
import simplenlg.phrasespec.*;
import simplenlg.features.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cfg")
public class NTLKRestController {

    private final NLTKService nltkService;

    @Autowired
    public NTLKRestController(NLTKService nltkService) {
        this.nltkService = nltkService;
    }

    @GetMapping("/sentence")
    public String tokenizeText() {
//        Lexicon lexicon = Lexicon.getDefaultLexicon();
//        NLGFactory nlgFactory = new NLGFactory(lexicon);
//        Realiser realiser = new Realiser(lexicon);
//
//        NPPhraseSpec np = nlgFactory.createNounPhrase("an", "apple");
//        np.setFeature(Feature.NUMBER, NumberAgreement.PLURAL);
//        //System.out.println(np);
//
//        SPhraseSpec p = nlgFactory.createClause();
//        p.setSubject("Kh");
//        p.setVerb("buy");
//        p.setObject("the boy");
//
//        p.setFeature(Feature.INTERROGATIVE_TYPE, InterrogativeType.YES_NO);
////
////        p.setFeature(Feature.NEGATED, true);
////
//        String output2 = realiser.realiseSentence(p); // Realiser created earlier.
//        System.out.println(output2);
//
//        return "";
        return nltkService.buildSentence(new LevelId(3, 1));
    }

    @GetMapping("/sentences/{stageId}/{levelId}/{amount}")
    public List<String> getSentences(
            @PathVariable int stageId,
            @PathVariable int levelId,
            @PathVariable int amount) {
        List<String> results = new ArrayList<>();
        for (int i = 0; i < amount; ++i) {
            results.add(nltkService.buildSentence(new LevelId(stageId, levelId)));
        }

        return results;
    }

}
