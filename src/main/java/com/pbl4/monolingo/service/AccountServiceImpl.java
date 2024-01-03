package com.pbl4.monolingo.service;

import com.pbl4.monolingo.controller.FriendController;
import com.pbl4.monolingo.dao.AccountRepository;
import com.pbl4.monolingo.dao.DataPerDayRepository;
import com.pbl4.monolingo.dao.ExtraInformationRepository;
import com.pbl4.monolingo.dao.FriendRepository;
import com.pbl4.monolingo.entity.Account;
import com.pbl4.monolingo.entity.ExtraInformation;
import com.pbl4.monolingo.entity.embeddable.FriendId;
import com.pbl4.monolingo.security.MD5PasswordEncoder;
import com.pbl4.monolingo.utility.dto.SearchFriend;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final ExtraInformationRepository extraInformationRepository;
    private final PasswordEncoder passwordEncoder;
    private final FriendRepository friendRepository;

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

            if(account.getExtraInformation() == null)
            {
                account.setExtraInformation(new ExtraInformation());
            }

            ExtraInformation newExtra = account.getExtraInformation();
            newExtra.setHearts(5);
            newExtra.setBalance(0);
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

            System.out.println(account.getExtraInformation());
            temp.getExtraInformation().setBalance(account.getExtraInformation().getBalance());
            temp.setType(account.getType());
            temp.setEnabled(account.getEnabled());

            accountRepository.save(temp);
        }
    }

    @Override
    public void updateAccount(Account account) {
        Account newAccount = accountRepository.findById(account.getAccountId()).get();
        newAccount.setName(account.getName());
        newAccount.setBirthdate(account.getBirthdate());
        newAccount.setGender(account.getGender());
        newAccount.setEmail(account.getEmail());
        accountRepository.save(newAccount);
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
    public List<SearchFriend> searchFriend(String keyword, int currentId) {
        List<SearchFriend> results = new ArrayList<>();
        List<Account> accounts = accountRepository.searchAccountByName(keyword);

        for (Account account : accounts) {
            if (currentId == account.getAccountId()) continue;
            boolean friended = friendRepository.existsById(new FriendId(currentId, account.getAccountId()));
            results.add(new SearchFriend(account.getAccountId(), account.getUsername(), account.getName(), friended));
        }

        return results;
    }

    @Override
    public List<Account> searchAccountByEmail(String email) {
        return accountRepository.searchAccountByEmail(email);
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

    @Override
    public List<Account> getAccountByUsernameAndEmail(String username, String email) {
        return accountRepository.getAccountByUsernameAndEmail(username,email);
    }

    @Override
    public List<Account> searchAccountByUsername(String username) {
        return accountRepository.getAccountByUsername(username);
    }
}
