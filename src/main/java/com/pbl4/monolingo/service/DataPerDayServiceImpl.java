package com.pbl4.monolingo.service;

import com.pbl4.monolingo.dao.AccountRepository;
import com.pbl4.monolingo.dao.DataPerDayRepository;
import com.pbl4.monolingo.dao.FriendRepository;
import com.pbl4.monolingo.entity.Account;
import com.pbl4.monolingo.entity.DataPerDay;
import com.pbl4.monolingo.entity.embeddable.DataPerDayId;
import com.pbl4.monolingo.entity.embeddable.FriendId;
import com.pbl4.monolingo.utility.dto.AccountDPDStat;
import com.pbl4.monolingo.utility.dto.AccountExp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import com.pbl4.monolingo.utility.dto.AccountStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@AllArgsConstructor
public class DataPerDayServiceImpl implements DataPerDayService {


    private final DataPerDayRepository dataPerDayRepository;
    private final FriendRepository friendRepository;
    private final AccountRepository accountRepository;

    @Override
    public List<DataPerDay> getAccountDPDs(Integer accountId) {
        return dataPerDayRepository.findAllByIdAccountId(accountId);
    }

    @Override
    public AccountDPDStat getAccountDPDStat(Integer accountId) {
        List<Integer> exps = new ArrayList<>();
        List<Integer> onlineHours = new ArrayList<>();
        List<String> days = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter rFormatter = DateTimeFormatter.ofPattern("MM-dd");

        List<DataPerDay> dpds = getAccountDPDs(accountId);

        int minId = dpds.get(0).getId().getDayId();
        int maxId = dpds.get(dpds.size() - 1).getId().getDayId();
        LocalDate firstDate = LocalDate.parse(String.valueOf(minId), formatter);

        LocalDate minDay = LocalDate.parse(String.valueOf(minId), formatter);
        LocalDate maxDay = LocalDate.parse(String.valueOf(maxId), formatter);
        int offset = (int) ChronoUnit.DAYS.between(minDay, maxDay);
        System.out.println(offset);


        for (int i = 0; i <= offset; ++i) {
            days.add(firstDate.plusDays(i).format(rFormatter));
            exps.add(0);
            onlineHours.add(0);
        }

        for (DataPerDay d : dpds) {
            LocalDate current = LocalDate.parse(String.valueOf(d.getId().getDayId()), formatter);
            int index = (int) ChronoUnit.DAYS.between(minDay, current);

            exps.set(index, d.getExperience());
            onlineHours.set(index, d.getOnlineHours().intValue());
        }

        return new AccountDPDStat(exps, onlineHours, days);
    }

    @Override
    public DataPerDay getAccountDPD(Integer accountId) {
        Optional<DataPerDay> result = dataPerDayRepository.findById(new DataPerDayId(getDayId(), accountId));

        return result.orElse(null);
    }


    @Override
    public DataPerDay updateAccountDPD(Integer accountId, int exp, float onlHours) {
        DataPerDay dpd = getAccountDPD(accountId);

        if (dpd == null) {
            dpd = new DataPerDay(new DataPerDayId(getDayId(), accountId), 0, 0f);
            dpd.setAccount(accountRepository.findById(accountId).get());
            dataPerDayRepository.save(dpd);
        }

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
                (int)results.get(0)[3],
                false);
    }

    @Override
    public AccountStats getAccountStats(int accountId, int currentId) {
        List<Object[]> results = dataPerDayRepository.findAccountBySumStats(accountId);
        boolean friended = friendRepository.existsById(new FriendId(accountId, currentId));

        return new AccountStats(
                (Account)results.get(0)[0],
                (Long)results.get(0)[1],
                (Double)results.get(0)[2],
                (int)results.get(0)[3],
                friended);
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
