package com.pbl4.monolingo.service;

import com.pbl4.monolingo.utility.cfg.ContextFreeGrammar;
import com.pbl4.monolingo.utility.cfg.ProductionRule;
import com.pbl4.monolingo.utility.cfg.SentenceBuilder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class NLTKServiceImpl implements NLTKService {

    private ContextFreeGrammar cfg;

    public NLTKServiceImpl() {
        initCfg();
    }

    @Override
    public String CFG() {

        SentenceBuilder sentenceBuilder = cfg.getSentenceBuilder();
//        System.out.print(sentenceBuilder.getTemplateSentence() + "----");

        return sentenceBuilder.build().toString();
    }

    private void initCfg() {
        ProductionRule rule1 = new ProductionRule("S", Arrays.asList(
                "NP1 VP1",
                "NP2 VP2",
                "S1"));
        ProductionRule rule4 = new ProductionRule("S1", Arrays.asList(
                "NP2",
                "NP2 and,và NP2"
        ));
        ProductionRule rule2 = new ProductionRule("NP1", Arrays.asList(
                "Pronoun",
                "People"
        ));
        ProductionRule rule3 = new ProductionRule("NP2", Arrays.asList(
                "DetNor",
                "Place"
        ));
        ProductionRule rule5 = new ProductionRule("DetNor", Arrays.asList(
                "Det1 Nor1",
                "Det2 Nor2",
                "Det3 Nor3"));
        ProductionRule rule6 = new ProductionRule("VP1", Arrays.asList(
                "+Verb1", "+Verb2 O"
        ));
        ProductionRule rule7 = new ProductionRule("VP2", Arrays.asList("Be Adj"));

        cfg = new ContextFreeGrammar();
        cfg.addProductionRule(rule1);
        cfg.addProductionRule(rule2);
        cfg.addProductionRule(rule3);
        cfg.addProductionRule(rule4);
        cfg.addProductionRule(rule5);
        cfg.addProductionRule(rule6);
        cfg.addProductionRule(rule7);
    }

}