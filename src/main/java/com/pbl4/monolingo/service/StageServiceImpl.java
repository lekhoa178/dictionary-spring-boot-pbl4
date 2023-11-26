package com.pbl4.monolingo.service;

import com.pbl4.monolingo.dao.StageRepository;
import com.pbl4.monolingo.entity.Stage;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class StageServiceImpl implements StageService{
    private final StageRepository stageRepository;

    public StageServiceImpl(StageRepository stageRepository) {
        this.stageRepository = stageRepository;
    }

    @Override
    public List<Stage> getAllStage() {
        return stageRepository.findAll();
    }

    @Override
    public Stage getStageById(int id) {
        Optional<Stage> stage = stageRepository.findById(id);
        Stage rs = null;

        if (stage.isPresent())
            rs = stage.get();

        return rs;
    }

    @Override
    public void save(Stage stage) {
        stageRepository.save(stage);
    }
}
