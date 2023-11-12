package com.pbl4.monolingo.service;

import com.pbl4.monolingo.entity.Lexicon;
import com.pbl4.monolingo.entity.Synset;
import com.pbl4.monolingo.utility.uimodel.DefinitionDetailView;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public interface DictionaryService {

    List<Synset> getSynsetsByWord(String word);

    List<Lexicon> searchByWord(String word);

    List<String> searchDistinctByWord(String word);
    HashMap<String, List<DefinitionDetailView>> getDefinitionByWord(String word);

    List<Lexicon> getSimilarsBySynsetId(BigDecimal synsetId);

    List<Lexicon> getAntonymsBySynsetId(BigDecimal synsetId);

    List<Lexicon> getDerivedBySynsetId(BigDecimal synsetId);
}
