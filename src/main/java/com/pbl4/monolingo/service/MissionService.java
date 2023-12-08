package com.pbl4.monolingo.service;

import com.pbl4.monolingo.entity.Mission;

import java.util.List;

public interface MissionService {
    List<Mission> getAllMissions();
    Mission getMissionById(int id);
    void saveMission(Mission mission);
    void deleteMission(int id);
}
