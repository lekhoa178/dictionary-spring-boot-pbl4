package com.pbl4.monolingo.service;

import com.pbl4.monolingo.entity.DailyMission;
import com.pbl4.monolingo.entity.Mission;

import java.util.List;

public interface DailyMissionService {

    List<Mission> getRandomMission(int amount);
    List<DailyMission> getMissionByAccountId(int dayId, int accountId);
    void initMission(int accountId, int amountId);
    boolean hasReceivedMission(int accountId);
    int updateDailyMission(int accountId, int exp, float precise, String type);

}
