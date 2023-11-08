package com.pbl4.monolingo.service;

import com.pbl4.monolingo.entity.Account;
import com.pbl4.monolingo.entity.ExtraInformation;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AccountService {

    ExtraInformation getAccountInfoByUsername(String username);
    List<Account> getAllAccount();
    Page<Account> getAccountWithPage(int offset, int size);
    void addAccount(Account account);
}
