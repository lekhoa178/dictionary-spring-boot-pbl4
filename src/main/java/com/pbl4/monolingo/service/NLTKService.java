package com.pbl4.monolingo.service;

import com.pbl4.monolingo.utility.cfg.ContextFreeGrammar;
import com.pbl4.monolingo.utility.cfg.ProductionRule;
import com.pbl4.monolingo.utility.cfg.SentenceBuilder;
import com.pbl4.monolingo.utility.enplural.English;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class NLTKService {

    public void CFG() {

//        System.out.println(English.plural("kitchen", 2));
//        System.out.println(English.plural("knife", 2));

        ProductionRule rule1 = new ProductionRule("S", Arrays.asList("NP VP", "DetNor"));
        ProductionRule rule2 = new ProductionRule("NP", Arrays.asList(
                "Pronoun",
                "PNoun",
                "DetNor"));
        ProductionRule rule4 = new ProductionRule("PNoun", Arrays.asList("People", "Place"));
        ProductionRule rule5 = new ProductionRule("DetNor", Arrays.asList(
                "Det1 Nor1",
                "Det2 Nor2",
                "Det3 Nor3"));
        ProductionRule rule3 = new ProductionRule("VP", Arrays.asList("Verb O", "Verb"));

        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProductionRule(rule1);
        cfg.addProductionRule(rule2);
        cfg.addProductionRule(rule3);
        cfg.addProductionRule(rule4);
        cfg.addProductionRule(rule5);

        SentenceBuilder sentenceBuilder = cfg.getSentenceBuilder();
        System.out.print(sentenceBuilder.getTemplateSentence() + "----");
        System.out.println(sentenceBuilder.build());
    }
}