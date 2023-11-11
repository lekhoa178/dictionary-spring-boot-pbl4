package com.pbl4.monolingo.rest;

import com.pbl4.monolingo.service.NLTKService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import simplenlg.framework.*;
import simplenlg.lexicon.*;
import simplenlg.realiser.english.*;
import simplenlg.phrasespec.*;
import simplenlg.features.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class NTLKRestController {

    private final NLTKService nltkService;

    @Autowired
    public NTLKRestController(NLTKService nltkService) {
        this.nltkService = nltkService;
    }

    @GetMapping("/cfg/sentence")
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
//        p.setSubject(np);
//        p.setVerb("be");
//        p.setObject("the boy");
//
//        p.setFeature(Feature.NEGATED, true);
//
//        String output2 = realiser.realiseSentence(p); // Realiser created earlier.
//        System.out.println(output2);
//
//        return "";
        return nltkService.CFG();
    }

    @GetMapping("/cfg/sentences/{amount}")
    public List<String> getSeteneces(@PathVariable int amount) {
        List<String> results = new ArrayList<>();
        for (int i = 0; i < amount; ++i) {
            results.add(nltkService.CFG());
        }

        return results;
    }

}
