package com.pbl4.monolingo.service;

import com.pbl4.monolingo.entity.Account;
import com.pbl4.monolingo.entity.ExtraInformation;

public interface AccountService {

    ExtraInformation getAccountInfoByUsername(String username);
    Account getAccountByUsername(String username);
    void save(Account account);
}
