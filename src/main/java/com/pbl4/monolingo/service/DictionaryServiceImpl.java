package com.pbl4.monolingo.service;

import com.pbl4.monolingo.dao.*;
import com.pbl4.monolingo.entity.*;
import com.pbl4.monolingo.utility.uimodel.DefinitionDetailView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
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
                                 DerivedRepository derivedRepository,
                                 NotebookRepository notebookRepository) {
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
    public HashMap<String, List<DefinitionDetailView>> getDefinitionByWord(String word) {
        HashMap<String, List<Synset>> defMap = new HashMap<>(){{
            put("DANH TỪ", new ArrayList<>());
            put("ĐỘNG TỪ", new ArrayList<>());
            put("TÍNH TỪ", new ArrayList<>());
            put("TRẠNG TỪ", new ArrayList<>());
        }};

        List<Synset> synsets = getSynsetsByWord(word);

        for (Synset syn : synsets) {
            defMap.get("TRẠNG TỪ");
            List<Synset> def = switch ((int) Math.floor(syn.getSynsetId().divide(new BigDecimal(100000000)).intValue())) {
                case 1 -> defMap.get("DANH TỪ");
                case 2 -> defMap.get("ĐỘNG TỪ");
                case 3 -> defMap.get("TÍNH TỪ");
                case 4 -> defMap.get("TRẠNG TỪ");
                default -> defMap.get("TRẠNG TỪ");
            };

            def.add(syn);

        }

        return getDefDetailMap(defMap);

    }


    @Override
    public List<Lexicon> getSimilarsBySynsetId(BigDecimal synsetId) {

        List<BigDecimal> synsetIds = similarRepository
                .findByIdSynsetId1(synsetId)
                .stream().map(sim -> sim.getId().getSynsetId2()).toList();

        return getLexiconsBySynsetsId(synsetIds);

    }

    @Override
    public List<Lexicon> getAntonymsBySynsetId(BigDecimal synsetId) {
        List<BigDecimal> synsetIds = antonymRepository
                .findByIdLexiconId1SynsetId(synsetId)
                .stream().map(ant -> ant.getId().getLexiconId2().getSynsetId()).toList();

        return getLexiconsBySynsetsId(synsetIds);
    }

    @Override
    public List<Lexicon> getDerivedBySynsetId(BigDecimal synsetId) {
        List<BigDecimal> synsetIds = derivedRepository
                .findByIdLexiconId1SynsetId(synsetId)
                .stream().map(sim -> sim.getId().getLexiconId2().getSynsetId()).toList();

        return getLexiconsBySynsetsId(synsetIds);
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

    private HashMap<String, List<DefinitionDetailView>> getDefDetailMap(HashMap<String, List<Synset>> defMap) {
        HashMap<String, List<DefinitionDetailView>> results = new HashMap<>();

        for (Map.Entry<String, List<Synset>> d : defMap.entrySet()) {
            if (d.getValue().isEmpty()) continue;
            else {
                List<DefinitionDetailView> details = new ArrayList<>();

                List<Synset> str = d.getValue();
                // foreach synset of words
                for (Synset s : str) {
                    List<String> examples;
                    List<String> synonyms;
                    List<String> antonyms;

                    List<String> splt = List.of(s.getDefinition().split(";"));
                    examples = splt.subList(1, splt.size());

                    synonyms = getSimilarsBySynsetId(s.getSynsetId())
                            .stream().map(Lexicon::getWord).toList();
                    antonyms = getAntonymsBySynsetId(s.getSynsetId())
                            .stream().map(Lexicon::getWord).toList();


                    DefinitionDetailView dtl = new DefinitionDetailView(splt.get(0),
                            examples,
                            synonyms,
                            antonyms);

                    details.add(dtl);
                }

                results.put(d.getKey(), details);
            }
        }
        return results;
    }
}
