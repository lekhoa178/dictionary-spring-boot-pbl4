package com.pbl4.monolingo.service;

import com.pbl4.monolingo.dao.AccountRepository;
import com.pbl4.monolingo.dao.ExtraInformationRepository;
import com.pbl4.monolingo.entity.Account;
import com.pbl4.monolingo.entity.ExtraInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired

    private AccountRepository accountRepository;
    @Autowired
    private ExtraInformationRepository extraInformationRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, ExtraInformationRepository extraInformationRepository) {
        this.accountRepository = accountRepository;
        this.extraInformationRepository = extraInformationRepository;
    }

    @Override
    public ExtraInformation getAccountInfoByUsername(String username) {
        return extraInformationRepository.findExtraInformationByAccountId(
                accountRepository.findAccountByUsername(username).getAccountId());
    }

    @Override
    public Account getAccountByUsername(String username) {
        return accountRepository.findAccountByUsername(username);
    }

    @Override
    public void save(Account account) {

    }
}
