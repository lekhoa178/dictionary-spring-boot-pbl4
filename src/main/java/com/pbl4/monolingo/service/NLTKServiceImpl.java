package com.pbl4.monolingo.service;

import com.pbl4.monolingo.dao.VocabularyRepository;
import com.pbl4.monolingo.entity.Vocabulary;
import com.pbl4.monolingo.entity.embeddable.LevelId;
import com.pbl4.monolingo.entity.embeddable.VocabularyId;
import com.pbl4.monolingo.utility.cfg.ContextFreeGrammar;
import com.pbl4.monolingo.utility.cfg.ProductionRule;
import com.pbl4.monolingo.utility.cfg.SentenceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NLTKServiceImpl implements NLTKService {

    private final VocabularyRepository vocabularyRepository;

    private ContextFreeGrammar cfg;

    @Autowired
    public NLTKServiceImpl(VocabularyRepository vocabularyRepository) {
        this.vocabularyRepository = vocabularyRepository;

        initCfg();
    }

    @Override
    public String buildSentence(LevelId id) {

        List<Vocabulary> vocabs = vocabularyRepository.findAllByIdLevelId(id);

        SentenceBuilder sentenceBuilder = cfg.getSentenceBuilder();
        setupBuilder(sentenceBuilder, vocabs);

        System.out.println(sentenceBuilder.getTemplateSentence() + "----");

        return sentenceBuilder.build().toString();
    }

    private void setupBuilder(SentenceBuilder sentenceBuilder, List<Vocabulary> vocabs) {
        HashMap<String, List<String>> vocabMap = analyzeVocabs(vocabs);

        for (Map.Entry<String, List<String>> v : vocabMap.entrySet()) {
            switch (v.getKey()) {
                case "Nom":
                    sentenceBuilder.setNominals(v.getValue());
                    break;

                case "Verb":
                    sentenceBuilder.setVerbs(v.getValue());
                    break;

                case "AdjVerb":
                    sentenceBuilder.setAdjectives(v.getValue());
                    break;

                case "O":
                    sentenceBuilder.setObjects(v.getValue());
                    break;
            }
        }
    }

    private HashMap<String, List<String>> analyzeVocabs(List<Vocabulary> vocabs) {
        HashMap<String, List<String>> vocabMap = new HashMap<>();

        for (Vocabulary v : vocabs) {
            List<String> words = vocabMap.get(v.getType());
            if (words == null) {
                words = new ArrayList<>();
            } else {
                words.add(v.getWord() + "," + v.getMeaning());
            }

            vocabMap.put(v.getType(), words);
        }

        return vocabMap;
    }

    private void initCfg() {
        ProductionRule rule1 = new ProductionRule("S", Arrays.asList(
                "NP1 VP1",
                "NP2 VP2",
                "yn NP1 VP1",
                "yn NP2 VP2",
                "S1"));
        ProductionRule rule2 = new ProductionRule("S1", Arrays.asList(
                "NP2",
                "NP2 Conj NP2"
        ));
        ProductionRule rule3 = new ProductionRule("NP1", Arrays.asList(
                "Pronoun",
                "People"
        ));
        ProductionRule rule4 = new ProductionRule("NP2", Arrays.asList(
                "Nom"
        ));
        ProductionRule rule5 = new ProductionRule("VP1", Arrays.asList(
                "Verb O"
        ));
        ProductionRule rule6 = new ProductionRule("VP2", Arrays.asList("be AdjVerb"));

        cfg = new ContextFreeGrammar();
        cfg.addProductionRule(rule1);
        cfg.addProductionRule(rule2);
        cfg.addProductionRule(rule3);
        cfg.addProductionRule(rule4);
        cfg.addProductionRule(rule5);
        cfg.addProductionRule(rule6);
    }

}