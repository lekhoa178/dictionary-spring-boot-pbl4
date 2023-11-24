package com.pbl4.monolingo.service;

import com.pbl4.monolingo.entity.Account;
import com.pbl4.monolingo.entity.ExtraInformation;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AccountService {

    ExtraInformation getAccountInfoByUsername(String username);
    Account getAccountByUsername(String username);
//    void save(Account account);

    List<Account> getAllAccount();
    Page<Account> getAccountWithPage(int offset, int size);
    void saveAccount(Account account);
    Account getAccountById(int id);
    void deleteAccountById(int id);
    List<Account> searchAccount(String keyword);
    Account getAccountByEmail(String mail);
 }
