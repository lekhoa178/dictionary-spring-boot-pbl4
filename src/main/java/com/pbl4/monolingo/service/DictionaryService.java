package com.pbl4.monolingo.service;

import com.pbl4.monolingo.entity.Lexicon;
import com.pbl4.monolingo.entity.Synset;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface DictionaryService {

    List<Synset> getSynsetsByWord(String word);

    List<Lexicon> searchByWord(String word);

    List<String> searchDistinctByWord(String word);

    List<Lexicon> getSimilarsBySynsetId(BigDecimal synsetId);

    List<Lexicon> getAntonymsBySynsetId(BigDecimal synsetId);

    List<Lexicon> getDerivedBySynsetId(BigDecimal synsetId);


}
