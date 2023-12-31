package com.pbl4.monolingo.service;

import com.pbl4.monolingo.entity.Account;
import com.pbl4.monolingo.entity.ExtraInformation;
import com.pbl4.monolingo.utility.dto.SearchFriend;
import gov.nih.nlm.nls.lvg.Util.Str;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AccountService {

    ExtraInformation getAccountInfoByUsername(String username);
    Account getAccountByUsername(String username);
//    void save(Account account);

    List<Account> getAllAccount();
    Page<Account> getAccountWithPage(int offset, int size);
    void saveAccount(Account account);
    void updateAccount(Account account);
    Account getAccountById(int id);
    void deleteAccountById(int id);
    List<Account> searchAccount(String keyword);
    List<SearchFriend> searchFriend(String keyword, int currentId);
    List<Account> searchAccountByEmail(String email);
    Account getAccountByEmail(String mail);
    void changePassword(Account account, String newPassword);
    void updateProfile(String name, boolean gender, Date birthDate, String email);
    List<Account>getAccountByUsernameAndEmail(String username,String email);
    List<Account>searchAccountByUsername(String username);
 }
