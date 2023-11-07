package com.pbl4.monolingo.service;

import com.pbl4.monolingo.dao.*;
import com.pbl4.monolingo.entity.Derived;
import com.pbl4.monolingo.entity.Lexicon;
import com.pbl4.monolingo.entity.Similar;
import com.pbl4.monolingo.entity.Synset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class DictionaryServiceImpl implements DictionaryService {

    private final LexiconRepository lexiconRepository;
    private final SynsetRepository synsetRepository;
    private final SimilarRepository similarRepository;
    private final AntonymRepository antonymRepository;
    private final DerivedRepository derivedRepository;


    @Autowired
    public DictionaryServiceImpl(LexiconRepository lexiconRepository,
                                 SynsetRepository synsetRepository,
                                 SimilarRepository similarRepository,
                                 AntonymRepository antonymRepository,
                                 DerivedRepository derivedRepository) {
        this.lexiconRepository = lexiconRepository;
        this.synsetRepository = synsetRepository;
        this.similarRepository = similarRepository;
        this.antonymRepository = antonymRepository;
        this.derivedRepository = derivedRepository;
    }

    @Override
    public List<Synset> getSynsetsByWord(String word) {
        return lexiconRepository.findByWord(word).stream().map(Lexicon::getSynset).toList();
    }

    @Override
    public List<Lexicon> searchByWord(String word) {
        return lexiconRepository.findByWordStartsWith(word);
    }

    @Override
    public List<String> searchDistinctByWord(String word) {
        return lexiconRepository.findDistinctByWordStartsWith(word);
    }


    @Override
    public List<Lexicon> getSimilarsBySynsetId(BigDecimal synsetId) {

        List<BigDecimal> synsetIds = similarRepository
                .findByIdSynsetId1(synsetId)
                .stream().map(sim -> sim.getId().getSynsetId2()).toList();

        List<Lexicon> results = getLexiconsBySynsetsId(synsetIds);

        return results;

    }

    @Override
    public List<Lexicon> getAntonymsBySynsetId(BigDecimal synsetId) {
        List<BigDecimal> synsetIds = antonymRepository
                .findByIdLexiconId1SynsetId(synsetId)
                .stream().map(ant -> ant.getId().getLexiconId2().getSynsetId()).toList();

        List<Lexicon> results = getLexiconsBySynsetsId(synsetIds);

        return results;
    }

    @Override
    public List<Lexicon> getDerivedBySynsetId(BigDecimal synsetId) {
        List<BigDecimal> synsetIds = derivedRepository
                .findByIdLexiconId1SynsetId(synsetId)
                .stream().map(sim -> sim.getId().getLexiconId2().getSynsetId()).toList();

        List<Lexicon> results = getLexiconsBySynsetsId(synsetIds);

        return results;
    }


    private Synset getSynsetById(BigDecimal id) {
        Optional<Synset> result = synsetRepository.findById(id);

        Synset synset = null;

        if (result.isPresent())
            synset = result.get();
        else throw new RuntimeException("Did not find synset id - " + id);

        return synset;
    }

    private List<Synset> getSynsetsByIds(List<BigDecimal> ids) {
        return ids.stream().map(this::getSynsetById)
                .toList();
    }

    private List<Lexicon> getLexiconsBySynsetsId(List<BigDecimal> ids) {
        List<Lexicon> results = new ArrayList<>();

        ids.forEach(id ->
                results.addAll(lexiconRepository.findByIdSynsetId(id))
        );

        return results;
    }
}
