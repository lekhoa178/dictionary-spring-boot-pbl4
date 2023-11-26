package com.pbl4.monolingo.service;

import com.pbl4.monolingo.entity.embeddable.LevelId;
import com.pbl4.monolingo.utility.dto.StageLevelState;

import java.util.List;

public interface LearnService {

    List<StageLevelState> getAccountStages(int accountId);
    void finishLevel(int accountId, int stageId, int levelId);
}
