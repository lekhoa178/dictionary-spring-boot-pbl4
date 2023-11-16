package com.pbl4.monolingo.service;

import com.pbl4.monolingo.entity.Account;
import com.pbl4.monolingo.entity.DataPerDay;
import com.pbl4.monolingo.utility.dto.AccountExp;

import java.util.List;

public interface DataPerDayService {

    List<DataPerDay> getAccountDPDs(Integer accountId);

    DataPerDay getAccountDPD(Integer accountId);

    Integer getDayId();

    DataPerDay updateAccountDPD(Integer accountId, int exp, int onlHours);

    List<AccountExp> getAllAccountOrderBySumExp();

    AccountExp getAccountSumExpById(Integer accountId);

}
