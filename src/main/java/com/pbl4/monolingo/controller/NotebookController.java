package com.pbl4.monolingo.controller;

import com.pbl4.monolingo.entity.Account;
import com.pbl4.monolingo.rest.BotController;
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

    private final AccountService accountService;
    private final NotebookService notebookService;
    private final BotController botController;

    @Autowired
    public NotebookController(AccountService accountService, NotebookService notebookService,
                              BotController botController) {
        this.accountService = accountService;
        this.notebookService = notebookService;
        this.botController = botController;
    }


}
