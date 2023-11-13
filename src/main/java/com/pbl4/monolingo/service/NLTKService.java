package com.pbl4.monolingo.service;

import com.pbl4.monolingo.entity.embeddable.LevelId;
import com.pbl4.monolingo.entity.embeddable.VocabularyId;

public interface NLTKService {

    String buildSentence(LevelId id);

}
