package com.pbl4.monolingo.service;

import com.pbl4.monolingo.dao.AccountRepository;
import com.pbl4.monolingo.dao.DataPerDayRepository;
import com.pbl4.monolingo.dao.ExtraInformationRepository;
import com.pbl4.monolingo.entity.Account;
import com.pbl4.monolingo.entity.DataPerDay;
import com.pbl4.monolingo.entity.ExtraInformation;
import com.pbl4.monolingo.security.MD5PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;
    private ExtraInformationRepository extraInformationRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, ExtraInformationRepository extraInformationRepository, DataPerDayRepository dataPerDayRepository) {
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
    public void saveAccount(Account account) {
        if (account.getAccountId() == 0)
        {
            MD5PasswordEncoder md5PasswordEncoder = new MD5PasswordEncoder();
            account.setPassword(md5PasswordEncoder.encode(account.getPassword()));
        }
        ExtraInformation newExtra = account.getExtraInformation();
        newExtra.setHearts(5);
        newExtra.setNumberOfConsecutiveDay(0);
        newExtra.setNumberOfLoginDay(0);
        newExtra.setAccount(account);

        accountRepository.save(account);
    }

    @Override
    public Account getAccountById(int id) {
        Optional<Account> result = accountRepository.findById(id);
        Account account = null;
        if (result.isPresent())
            account = result.get();
        return account;
    }

    @Override
    public void deleteAccountById(int id) {
        accountRepository.deleteById(id);
    }

    @Override
    public List<Account> searchAccount(String keyword) {
        return accountRepository.searchAccount(keyword);
    }

    @Override
    public Account getAccountByUserName(String username) {
        return accountRepository.findAccountByUsername(username);
    }
}
