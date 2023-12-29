package com.pbl4.monolingo.controller;

import com.pbl4.monolingo.rest.BotRestController;
import com.pbl4.monolingo.service.AccountService;
import com.pbl4.monolingo.service.NotebookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/notebook")
public class NotebookController {

    private final AccountService accountService;
    private final NotebookService notebookService;
    private final BotRestController botRestController;

    @Autowired
    public NotebookController(AccountService accountService, NotebookService notebookService,
                              BotRestController botRestController) {
        this.accountService = accountService;
        this.notebookService = notebookService;
        this.botRestController = botRestController;
    }


}
