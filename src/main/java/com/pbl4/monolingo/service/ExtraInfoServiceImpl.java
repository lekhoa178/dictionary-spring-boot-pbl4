package com.pbl4.monolingo.service;

import com.pbl4.monolingo.dao.DataPerDayRepository;
import com.pbl4.monolingo.dao.ExtraInformationRepository;
import com.pbl4.monolingo.entity.Account;
import com.pbl4.monolingo.entity.DataPerDay;
import com.pbl4.monolingo.entity.ExtraInformation;
import com.pbl4.monolingo.entity.embeddable.DataPerDayId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Service
public class ExtraInfoServiceImpl implements ExtraInfoService {
    ExtraInformationRepository extraInformationRepository;
    DataPerDayRepository dataPerDayRepository;
    @Autowired
    public ExtraInfoServiceImpl(ExtraInformationRepository extraInformationRepository,
                                DataPerDayRepository dataPerDayRepository) {
        this.extraInformationRepository = extraInformationRepository;
        this.dataPerDayRepository = dataPerDayRepository;
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
        int restoreHeart = (int) (minutesSinceLost / 30);

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

    @Override
    public void updateExtraInfo(Account account) {
        int yesterdayId = Integer.parseInt(LocalDateTime.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        int currentDayId = Integer.parseInt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));

        DataPerDay yesterdayInfo = dataPerDayRepository.findById(new DataPerDayId(yesterdayId, account.getAccountId())).orElse(null);
        DataPerDay currentDayInfo = dataPerDayRepository.findById(new DataPerDayId(currentDayId, account.getAccountId())).orElse(null);

        ExtraInformation extraInformation = extraInformationRepository.findExtraInformationByAccountId(account.getAccountId());

        if (currentDayInfo == null) {
            if (yesterdayInfo == null)
                extraInformation.setNumberOfConsecutiveDay(1);
            else
                extraInformation.setNumberOfConsecutiveDay(extraInformation.getNumberOfConsecutiveDay() + 1);

            extraInformation.setNumberOfLoginDay(extraInformation.getNumberOfLoginDay() + 1);
            DataPerDay newDataPerDay = new DataPerDay(new DataPerDayId(currentDayId, account.getAccountId()), 0, 0F);
            newDataPerDay.setAccount(account);

            dataPerDayRepository.save(newDataPerDay);
        }
    }

    @Override
    public void buyHearts(int accountId) {
        ExtraInformation extraInformation = extraInformationRepository.findExtraInformationByAccountId(accountId);

        extraInformation.setHearts(5);
        extraInformation.setBalance(extraInformation.getBalance() - 200);
        extraInformation.setLostHeartTimes(null);

        extraInformationRepository.save(extraInformation);
    }
}
