package com.pbl4.monolingo.service;

import com.pbl4.monolingo.entity.Vocabulary;
import com.pbl4.monolingo.entity.embeddable.VocabularyId;

public interface VocabularyService {
    Vocabulary save(Vocabulary vocabulary);

    int findMaxId();

    void deleteVocabularyById(VocabularyId id);
}
