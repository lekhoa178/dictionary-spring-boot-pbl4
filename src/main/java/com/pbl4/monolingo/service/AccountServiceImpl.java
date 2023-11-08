package com.pbl4.monolingo.service;

import com.pbl4.monolingo.dao.AccountRepository;
import com.pbl4.monolingo.dao.ExtraInformationRepository;
import com.pbl4.monolingo.entity.Account;
import com.pbl4.monolingo.entity.ExtraInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;
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
    public List<Account> getAllAccount() {
        return accountRepository.findAll();
    }

    @Override
    public Page<Account> getAccountWithPage(int offset, int size) {
        return accountRepository.findAll(PageRequest.of(offset, size));
    }

    @Override
    public void addAccount(Account account) {
        accountRepository.save(account);
    }
}
