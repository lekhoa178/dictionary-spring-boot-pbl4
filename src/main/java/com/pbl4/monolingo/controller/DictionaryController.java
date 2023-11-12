package com.pbl4.monolingo.controller;

import com.pbl4.monolingo.entity.Account;
import com.pbl4.monolingo.service.AccountService;
import com.pbl4.monolingo.service.DictionaryService;
import com.pbl4.monolingo.service.NotebookService;
import com.pbl4.monolingo.utility.uimodel.DefinitionDetailView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

@Controller
public class DictionaryController {

    private final DictionaryService dictionaryService;
    private final AccountService accountService;
    private final NotebookService notebookService;

    @Autowired
    public DictionaryController(DictionaryService dictionaryService, AccountService accountService, NotebookService notebookService) {
        this.dictionaryService = dictionaryService;
        this.accountService = accountService;
        this.notebookService = notebookService;
    }

    @GetMapping("/meaning/{word}")

    public String showMeaning(Model model,
                              Principal principal,
                              @PathVariable String word,
                              @RequestHeader(value = "request-source", required = false) String requestSource) {

        Account account = accountService.getAccountByUserName(principal.getName());
        boolean isExist = notebookService.checkIsExsitInNotebook(account.getAccountId(), word);

        HashMap<String, List<DefinitionDetailView>> results = dictionaryService.getDefinitionByWord(word);

        model.addAttribute("definitions", results);
        model.addAttribute("word", word.replaceAll("_", " "));
        model.addAttribute("isExist", isExist);

        if (requestSource == null) {
            if (principal != null) {
                model.addAttribute("userData", accountService.getAccountInfoByUsername(principal.getName()));
            }
            return "main.html";
        }
        else
            return "fragments/meaning.html";
    }

}
