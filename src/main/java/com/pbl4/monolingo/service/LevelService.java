package com.pbl4.monolingo.service;

import com.pbl4.monolingo.entity.Level;
import com.pbl4.monolingo.entity.Vocabulary;
import com.pbl4.monolingo.entity.embeddable.LevelId;

import java.util.List;

public interface LevelService {
    List<Level> getLevelByStageId(int stageId);
    List<Vocabulary> getVocabularyByLevelId(LevelId levelId);

    void save(Level level);
}
