package com.pbl4.monolingo.rest;

import com.pbl4.monolingo.dao.LexiconRepository;
import com.pbl4.monolingo.dao.SynsetRepository;
import com.pbl4.monolingo.entity.Lexicon;
import com.pbl4.monolingo.entity.Synset;
import com.pbl4.monolingo.service.DictionaryService;
import com.pbl4.monolingo.utility.dto.DefinitionDetailView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/dictionary")
public class DictionaryRestController {

    private final DictionaryService dictionaryService;
    private final LexiconRepository lexiconRepository;
    private final SynsetRepository synsetRepository;

    @Autowired
    public DictionaryRestController(DictionaryService dictionaryService, LexiconRepository lexiconRepository, SynsetRepository synsetRepository) {
        this.dictionaryService = dictionaryService;
        this.lexiconRepository = lexiconRepository;
        this.synsetRepository = synsetRepository;
    }

    @GetMapping("/search/{word}/{count}")
    public List<Lexicon> searchLexicon(@PathVariable String word,@PathVariable int count) {
        return dictionaryService.searchByWord(word, count);
    }

    // expose "/search/{word}" return a list of Lexicon that have 'word' at the start
    @GetMapping("/search/{word}")
    public List<Lexicon> searchLexicon(@PathVariable String word) {
        return dictionaryService.searchByWord(word);
    }

//    @GetMapping("/search/{word}/{count}")
//    public List<String> searchLexiconLimit(@PathVariable String word, @PathVariable int count) {
//        return dictionaryService.searchDistinctByWord(word).stream().limit(count).toList();
//    }

    // expose "/synset/{word}" return a list of Synset
    @GetMapping("/synset/{word}")
    public List<Synset> getSynsetsByWord(@PathVariable String word) {
        return dictionaryService.getSynsetsByWord(word);
    }

    @GetMapping("/synset")
    public List<Synset> getSynsets() {
        return synsetRepository.findAll();
    }

    @GetMapping("/lexicon")
    public List<Lexicon> getLexicons() {
        return lexiconRepository.findAll();
    }

    @GetMapping("/lexicon/{word}")
    public List<Lexicon> getLexicons(@PathVariable String word) {
        return lexiconRepository.findByWord(word);
    }

    @GetMapping("/similar/{synsetId}")
    public List<Lexicon> getSimilarWords(@PathVariable BigDecimal synsetId) {
        return dictionaryService.getSimilarsBySynsetId(synsetId);
    }

    @GetMapping("/derived/{synsetId}")
    public List<Lexicon> getDerivedWords(@PathVariable BigDecimal synsetId) {
        return dictionaryService.getDerivedBySynsetId(synsetId);
    }

    @GetMapping("/antonym/{synsetId}")
    public List<Lexicon> getAntonymsWords(@PathVariable BigDecimal synsetId) {
        return dictionaryService.getAntonymsBySynsetId(synsetId);
    }

    @GetMapping("/definition/{word}")
    public HashMap<String, List<DefinitionDetailView>> getDefinitions(@PathVariable String word) {
        return dictionaryService.getDefinitionByWord(word);
    }

}
