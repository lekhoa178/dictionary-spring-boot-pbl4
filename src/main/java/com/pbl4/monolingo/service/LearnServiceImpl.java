package com.pbl4.monolingo.service;

import com.pbl4.monolingo.dao.LevelFulfillRepository;
import com.pbl4.monolingo.dao.StageRepository;
import com.pbl4.monolingo.entity.Level;
import com.pbl4.monolingo.entity.LevelFulfill;
import com.pbl4.monolingo.entity.Stage;
import com.pbl4.monolingo.entity.embeddable.LevelFulfillId;
import com.pbl4.monolingo.entity.embeddable.LevelId;
import com.pbl4.monolingo.utility.dto.LevelState;
import com.pbl4.monolingo.utility.dto.StageLevelState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Override
    public void finishLevel(int accountId, int stageId, int levelId) {
        LevelFulfill fulfill = new LevelFulfill(new LevelFulfillId(new LevelId(stageId, levelId), accountId));
        try {
            levelFulfillRepository.save(fulfill);
            System.out.println("???");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean isDoneLevel(int account, int stageId, int levelId) {
        Optional<LevelFulfill> fulfill = levelFulfillRepository.findById(new LevelFulfillId(new LevelId(stageId, levelId), account));
        return fulfill.isPresent();
    }

}