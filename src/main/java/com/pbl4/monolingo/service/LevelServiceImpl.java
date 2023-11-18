package com.pbl4.monolingo.service;

import com.pbl4.monolingo.dao.LevelRepository;
import com.pbl4.monolingo.dao.VocabularyRepository;
import com.pbl4.monolingo.entity.Level;
import com.pbl4.monolingo.entity.Vocabulary;
import com.pbl4.monolingo.entity.embeddable.LevelId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LevelServiceImpl implements LevelService{
    private final LevelRepository levelRepository;
    private final VocabularyRepository vocabularyRepository;

    public LevelServiceImpl(LevelRepository levelRepository, VocabularyRepository vocabularyRepository) {
        this.levelRepository = levelRepository;
        this.vocabularyRepository = vocabularyRepository;
    }

    @Override
    public List<Level> getLevelByStageId(int stageId) {
        return levelRepository.findById_StageId(stageId);
    }

    @Override
    public List<Vocabulary> getVocabularyByLevelId(LevelId levelId) {
        return vocabularyRepository.findAllByIdLevelId(levelId);
    }
}
