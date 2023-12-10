package com.pbl4.monolingo.rest;

import com.pbl4.monolingo.entity.Account;
import com.pbl4.monolingo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dictionary")
public class AccountRestController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/account/{username}")
    public Account getAccountByUsername(@PathVariable String username) {
        return accountService.getAccountByUsername(username);
    }
    @GetMapping("/accountById/{accountId}")
    public Account getAccountByAccountId(@PathVariable Integer accountId){
        Account account = accountService.getAccountById(accountId);
        return account;
    }
}