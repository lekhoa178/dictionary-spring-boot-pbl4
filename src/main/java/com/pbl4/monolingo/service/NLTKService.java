package com.pbl4.monolingo.service;

import com.pbl4.monolingo.utility.cfg.ContextFreeGrammar;
import com.pbl4.monolingo.utility.cfg.ProductionRule;
import com.pbl4.monolingo.utility.cfg.SentenceBuilder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class NLTKService {

    public void CFG() {
        ProductionRule rule1 = new ProductionRule("S", Arrays.asList("NP", "VP"));
        ProductionRule rule2 = new ProductionRule("NP", Arrays.asList("Pronoun", "PNoun"));
        ProductionRule rule3 = new ProductionRule("VP", Arrays.asList("Verb O", "Verb"));

        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProductionRule(rule1);
        cfg.addProductionRule(rule2);
        cfg.addProductionRule(rule3);

        SentenceBuilder sentenceBuilder = cfg.getSentenceBuilder();
        System.out.print(sentenceBuilder.getTemplateSentence() + "----");
        System.out.println(sentenceBuilder.build());
    }
}