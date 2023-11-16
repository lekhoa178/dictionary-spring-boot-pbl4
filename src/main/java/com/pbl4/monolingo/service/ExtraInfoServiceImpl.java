package com.pbl4.monolingo.service;

import com.pbl4.monolingo.dao.ExtraInformationRepository;
import com.pbl4.monolingo.entity.ExtraInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class ExtraInfoServiceImpl implements ExtraInfoService {
    ExtraInformationRepository extraInformationRepository;
    @Autowired
    public ExtraInfoServiceImpl(ExtraInformationRepository extraInformationRepository) {
        this.extraInformationRepository = extraInformationRepository;
    }

    @Override
    public void loseHeart(int accountId) {
        ExtraInformation userInfo = extraInformationRepository.findExtraInformationByAccountId(accountId);

        if (userInfo.getHearts() > 0) {
            userInfo.setHearts(userInfo.getHearts() - 1);

            if (userInfo.getLostHeartTimes() == null)
                userInfo.setLostHeartTimes(LocalDateTime.now());

            extraInformationRepository.save(userInfo);
        }
    }

    @Override
    public void restoreHeart(int accountId) {
        ExtraInformation userInfo = extraInformationRepository.findExtraInformationByAccountId(accountId);
        LocalDateTime lostHeartTime = userInfo.getLostHeartTimes();

        if (lostHeartTime == null)
            return;

        LocalDateTime currentTime = LocalDateTime.now();

        long minutesSinceLost = ChronoUnit.MINUTES.between(lostHeartTime, currentTime);
        int restoreHeart = (int) (minutesSinceLost);

        if (restoreHeart < 1)
            return;

        if (restoreHeart < (5 - userInfo.getHearts()))
        {
            userInfo.setHearts(userInfo.getHearts() + restoreHeart);
            userInfo.setLostHeartTimes(currentTime);
        }
        else {
            userInfo.setHearts(5);
            userInfo.setLostHeartTimes(null);
        }

        extraInformationRepository.save(userInfo);
    }
}
