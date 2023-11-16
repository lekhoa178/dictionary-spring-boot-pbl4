package com.pbl4.monolingo.controller;

import com.pbl4.monolingo.entity.Account;
import com.pbl4.monolingo.service.AccountService;
import com.pbl4.monolingo.service.NotebookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping("/notebook")
public class NotebookController {

    private AccountService accountService;
    private NotebookService notebookService;

    @Autowired
    public NotebookController(AccountService accountService, NotebookService notebookService) {
        this.accountService = accountService;
        this.notebookService = notebookService;
    }

    @PostMapping("/update")
    public void updateNotebook(@RequestParam String word, @RequestParam boolean isExist, Principal principal) {
        Account account = accountService.getAccountByUsername(principal.getName());
        if (isExist)
            notebookService.deleteNotebook(account.getAccountId(), word);
        else
            notebookService.addNotebook(account.getAccountId(), word);
    }

}
