package com.pbl4.monolingo.service;

import com.pbl4.monolingo.dao.AccountRepository;
import com.pbl4.monolingo.dao.DailyMissionRepository;
import com.pbl4.monolingo.dao.MissionRepository;
import com.pbl4.monolingo.entity.Account;
import com.pbl4.monolingo.entity.DailyMission;
import com.pbl4.monolingo.entity.Mission;
import com.pbl4.monolingo.entity.embeddable.DailyMissionId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DailyMissionServiceImpl implements DailyMissionService {

    private final MissionRepository missionRepository;
    private final DailyMissionRepository dailyMissionRepository;
    private final AccountService accountService;
    private final DataPerDayService dataPerDayService;

    @Autowired
    public DailyMissionServiceImpl(MissionRepository missionRepository,
                                   DailyMissionRepository dailyMissionRepository,
                                   DataPerDayService dataPerDayService,
                                   AccountService accountService) {
        this.missionRepository = missionRepository;
        this.dailyMissionRepository = dailyMissionRepository;
        this.dataPerDayService = dataPerDayService;
        this.accountService = accountService;
    }

    @Override
    public List<Mission> getRandomMission(int amount) {
        return missionRepository.findRandomMission(amount);
    }

    @Override
    public List<DailyMission> getMissionByAccountId(int dayId, int accountId) {
        return dailyMissionRepository.getAllByIdDayIdAndIdAccountId(dayId, accountId);
    }

    @Override
    public void initMission(int accountId, int amount) {
        if (hasReceivedMission(accountId)) return;

        int dayId = dataPerDayService.getDayId();
        Account account = accountService.getAccountById(accountId);
        List<Mission> missions = getRandomMission(amount);
        List<DailyMission> dailyMissions = new ArrayList<>();

        for (Mission m : missions) {
            dailyMissions.add(
                    new DailyMission(
                        new DailyMissionId(dayId, accountId, m.getId()),
                        m,
                        accountService.getAccountById(accountId),
                        (double)0));
        }

        dailyMissionRepository.saveAll(dailyMissions);
    }

    @Override
    public boolean hasReceivedMission(int accountId) {
        int dayId = dataPerDayService.getDayId();
        return !dailyMissionRepository.getAllByIdDayIdAndIdAccountId(dayId, accountId).isEmpty();
    }

    @Override
    public int updateDailyMission(int accountId, int exp, float precise, String type) {
        int earned = 0;

        List<DailyMission> dailyMissions = getMissionByAccountId(dataPerDayService.getDayId(), accountId);

        for (DailyMission dailyMission : dailyMissions) {
            if (isCorrect(dailyMission)) continue;

            int missionType = dailyMission.getMission().getType();

            if (type.equals("practice") && missionType == 3) {
                dailyMission.setProgress(dailyMission.getProgress() + 1);
            }

            if (precise > 80 && type.equals("learn")) {
                dailyMission.setProgress(dailyMission.getProgress() + 1);
            }

            if (missionType == 1) {
                dailyMission.setProgress((double) Math.round(dailyMission.getProgress() + exp));
            }

            if (dailyMission.getProgress() >= dailyMission.getMission().getTarget()) {
                earned += dailyMission.getMission().getPoint();
            }
        }

        dailyMissionRepository.saveAll(dailyMissions);

        return earned;
    }

    private boolean isCorrect(DailyMission dailyMission) {
        return dailyMission.getProgress() >= (dailyMission.getMission().getTarget());
    }

}
