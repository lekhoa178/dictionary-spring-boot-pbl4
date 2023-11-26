package com.pbl4.monolingo.service;

import com.pbl4.monolingo.dao.DataPerDayRepository;
import com.pbl4.monolingo.entity.Account;
import com.pbl4.monolingo.entity.DataPerDay;
import com.pbl4.monolingo.entity.embeddable.DataPerDayId;
import com.pbl4.monolingo.utility.dto.AccountExp;
import lombok.Getter;
import com.pbl4.monolingo.utility.dto.AccountStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DataPerDayServiceImpl implements DataPerDayService {


    private final DataPerDayRepository dataPerDayRepository;

    @Autowired
    public DataPerDayServiceImpl(DataPerDayRepository dataPerDayRepository) {
        this.dataPerDayRepository = dataPerDayRepository;
    }

    @Override
    public List<DataPerDay> getAccountDPDs(Integer accountId) {
        return dataPerDayRepository.findAllByIdAccountId(accountId);
    }

    @Override
    public DataPerDay getAccountDPD(Integer accountId) {
        Optional<DataPerDay> result = dataPerDayRepository.findById(new DataPerDayId(getDayId(), accountId));

        return result.orElse(null);
    }


    @Override
    public DataPerDay updateAccountDPD(Integer accountId, int exp, int onlHours) {
        DataPerDay dpd = getAccountDPD(accountId);
        dpd.setExperience(dpd.getExperience() + exp);
        dpd.setOnlineHours(dpd.getOnlineHours() + onlHours);

        return dataPerDayRepository.save(dpd);
    }

    @Override
    public AccountStats getAccountStats(int accountId) {
        List<Object[]> results = dataPerDayRepository.findAccountBySumStats(accountId);
        return new AccountStats(
                (Account)results.get(0)[0],
                (Long)results.get(0)[1],
                (Double)results.get(0)[2],
                (int)results.get(0)[3]);
    }

    @Override
    public List<AccountExp> getAllAccountOrderBySumExp() {
        List<Object[]> results = dataPerDayRepository.findAllAccountOrderBySumExp().stream().limit(20).toList();
        return results.stream().map(e -> new AccountExp((Account)e[0], (Long)e[1])).toList();
    }

    @Override
    public AccountExp getAccountSumExpById(Integer accountId) {
        List<DataPerDay> dpds = getAccountDPDs(accountId);

        Long exp = 0L;
        for (DataPerDay d : dpds) {
            exp += d.getExperience();
        }

        return new AccountExp(dpds.get(0).getAccount(), exp);
    }

    @Override
    public void save(DataPerDay dataPerDay) {
        dataPerDayRepository.save(dataPerDay);
    }

    @Override
    public Integer getDayId() {
        LocalDate currentDate = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return Integer.valueOf(currentDate.format(formatter));
    }

}
