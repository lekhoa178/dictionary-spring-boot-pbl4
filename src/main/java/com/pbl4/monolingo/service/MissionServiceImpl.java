package com.pbl4.monolingo.service;

import com.pbl4.monolingo.dao.MissionRepository;
import com.pbl4.monolingo.entity.Mission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MissionServiceImpl implements MissionService {
    private final MissionRepository missionRepository;

    @Autowired
    public MissionServiceImpl(MissionRepository missionRepository) {
        this.missionRepository = missionRepository;
    }

    @Override
    public List<Mission> getAllMissions() {
        return missionRepository.findAll();
    }

    @Override
    public Mission getMissionById(int id) {
        Optional<Mission> rs = missionRepository.findById(id);
        Mission mission = null;

        if (rs.isPresent())
            mission = rs.get();
        return mission;
    }

    @Override
    public void saveMission(Mission mission) {
        missionRepository.save(mission);
    }

    @Override
    public void deleteMission(int id) {
        missionRepository.deleteById(id);
    }
}
