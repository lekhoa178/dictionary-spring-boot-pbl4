package com.pbl4.monolingo.service;

import com.pbl4.monolingo.dao.LevelFulfillRepository;
import com.pbl4.monolingo.dao.StageRepository;
import com.pbl4.monolingo.entity.Level;
import com.pbl4.monolingo.entity.Stage;
import com.pbl4.monolingo.entity.embeddable.LevelFulfillId;
import com.pbl4.monolingo.utility.dto.LevelState;
import com.pbl4.monolingo.utility.dto.StageLevelState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LearnServiceImpl implements LearnService {

    private final StageRepository stageRepository;
    private final LevelFulfillRepository levelFulfillRepository;

    @Autowired
    public LearnServiceImpl(StageRepository stageRepository, LevelFulfillRepository levelFulfillRepository) {
        this.stageRepository = stageRepository;
        this.levelFulfillRepository = levelFulfillRepository;
    }

    @Override
    public List<StageLevelState> getAccountStages(int accountId) {
        List<StageLevelState> results = new ArrayList<>();

        List<Stage> stages = stageRepository.findAll();
        for (Stage s : stages) {

            List<LevelState> levelStates = new ArrayList<>();

            StageLevelState slt = new StageLevelState();
            slt.setStageId(s.getStageId());
            slt.setDepiction(s.getDepiction());
            slt.setTitle(s.getTitle());

            List<Level> levels = s.getLevels();
            for (Level l : levels) {
                    levelStates.add(new LevelState(
                            l.getId().getLevelId(),
                            levelFulfillRepository.findById(new LevelFulfillId(l.getId(), accountId)).isPresent()));
            }

            slt.setLevels(levelStates);
            results.add(slt);
        }

        return results;
    }


}
