package com.pbl4.monolingo.service;

import com.pbl4.monolingo.entity.Lexicon;
import com.pbl4.monolingo.entity.Synset;

import java.math.BigDecimal;
import java.util.List;

public interface DictionaryService {

    List<Synset> getSynsetsByWord(String word);

    List<Lexicon> searchByWord(String word);
    List<Lexicon> searchLimitByWord(String word, int limit);

    List<Lexicon> getSimilarsBySynsetId(BigDecimal synsetId);

    List<Lexicon> getAntonymsBySynsetId(BigDecimal synsetId);

    List<Lexicon> getDerivedBySynsetId(BigDecimal synsetId);


}
