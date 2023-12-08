package com.pbl4.monolingo.service;

import com.pbl4.monolingo.dao.VocabularyRepository;
import com.pbl4.monolingo.entity.Vocabulary;
import com.pbl4.monolingo.entity.embeddable.VocabularyId;
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
        return vocabularyRepository.save(vocabulary);
    }

    @Override
    public int findMaxId()
    {
        return vocabularyRepository.findMaxId();
    }

    @Override
    public void deleteVocabularyById(VocabularyId id) {
        vocabularyRepository.deleteById(id);
    }


}
