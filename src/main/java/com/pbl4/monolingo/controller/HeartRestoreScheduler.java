package com.pbl4.monolingo.controller;

import com.pbl4.monolingo.entity.Account;
import com.pbl4.monolingo.service.AccountService;
import com.pbl4.monolingo.service.ExtraInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HeartRestoreScheduler {
    private ExtraInfoService extraInfoService;

    private AccountService accountService;
    @Autowired
    public HeartRestoreScheduler(ExtraInfoService extraInfoService, AccountService accountService) {
        this.extraInfoService = extraInfoService;
        this.accountService = accountService;
    }

    @Scheduled(fixedRate = 5000)
    public void restoreHearts() {
        List<Account> accounts = accountService.getAllAccount();
        for (Account account: accounts) {
            extraInfoService.restoreHeart(account.getAccountId());
        }
    }
}
