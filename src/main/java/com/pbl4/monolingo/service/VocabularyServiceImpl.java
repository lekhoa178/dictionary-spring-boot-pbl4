package com.pbl4.monolingo.service;

import com.pbl4.monolingo.dao.VocabularyRepository;
import com.pbl4.monolingo.entity.Vocabulary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VocabularyServiceImpl implements VocabularyService{
    private final VocabularyRepository vocabularyRepository;

    @Autowired
    public VocabularyServiceImpl(VocabularyRepository vocabularyRepository) {
        this.vocabularyRepository = vocabularyRepository;
    }

    @Override
    public Vocabulary save(Vocabulary vocabulary) {
        System.out.println("Saving " + vocabulary.getId().getLevelId().getStageId() + " " + vocabulary.getId().getLevelId().getLevelId() + " " +
                vocabulary.getId().getVocabularyNum());
        return vocabularyRepository.save(vocabulary);
    }
}
