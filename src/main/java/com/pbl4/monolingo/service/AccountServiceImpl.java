package com.pbl4.monolingo.service;

import com.pbl4.monolingo.dao.AccountRepository;
import com.pbl4.monolingo.dao.DataPerDayRepository;
import com.pbl4.monolingo.dao.ExtraInformationRepository;
import com.pbl4.monolingo.entity.Account;
import com.pbl4.monolingo.entity.ExtraInformation;
import com.pbl4.monolingo.security.MD5PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired

    private AccountRepository accountRepository;
    @Autowired
    private ExtraInformationRepository extraInformationRepository;
    @Autowired
    private  PasswordEncoder passwordEncoder;

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
    public Account getAccountByUsername(String username) {
        return accountRepository.findAccountByUsername(username);
    }

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

            ExtraInformation newExtra = account.getExtraInformation();
            newExtra.setHearts(5);
            newExtra.setNumberOfConsecutiveDay(0);
            newExtra.setNumberOfLoginDay(0);
            newExtra.setAccount(account);

            accountRepository.save(account);
        }
        else {
            Account temp = getAccountById(account.getAccountId());
            temp.setEmail(account.getEmail());
            temp.setName(account.getName());
            temp.setBirthdate(account.getBirthdate());
            temp.setGender(account.getGender());
            temp.getExtraInformation().setBalance(account.getExtraInformation().getBalance());
            temp.setType(account.getType());
            temp.setEnabled(account.getEnabled());

            accountRepository.save(temp);
        }


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
    public Account getAccountByEmail(String mail) {
        return accountRepository.findByEmail(mail);
    }

    @Override
    public void changePassword(Account account, String newPassword) {
        account.setPassword(passwordEncoder.encode(newPassword));
        accountRepository.save(account);
    }

    @Override
    public void updateProfile(String name, boolean gender, Date birthDate, String email) {

    }
}
