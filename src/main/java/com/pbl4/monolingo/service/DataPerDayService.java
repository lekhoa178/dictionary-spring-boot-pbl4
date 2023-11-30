package com.pbl4.monolingo.service;

import com.pbl4.monolingo.entity.Account;
import com.pbl4.monolingo.entity.DataPerDay;
import com.pbl4.monolingo.utility.dto.AccountDPDStat;
import com.pbl4.monolingo.utility.dto.AccountExp;
import com.pbl4.monolingo.utility.dto.AccountStats;

import javax.xml.crypto.Data;
import java.util.List;

public interface DataPerDayService {

    List<DataPerDay> getAccountDPDs(Integer accountId);

    DataPerDay getAccountDPD(Integer accountId);

    AccountDPDStat getAccountDPDStat(Integer accountId);

    Integer getDayId();

    DataPerDay updateAccountDPD(Integer accountId, int exp, float onlHours);

    AccountStats getAccountStats(int accountId);

    List<AccountExp> getAllAccountOrderBySumExp();

    AccountExp getAccountSumExpById(Integer accountId);

    void save(DataPerDay dataPerDay);
}
