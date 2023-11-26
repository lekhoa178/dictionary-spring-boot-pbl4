package com.pbl4.monolingo.service;

import com.pbl4.monolingo.entity.Stage;

import java.util.List;

public interface StageService {

    List<Stage> getAllStage();
    Stage getStageById(int id);

    void save(Stage stage);
}
