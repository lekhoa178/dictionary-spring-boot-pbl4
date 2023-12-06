package com.pbl4.monolingo.rest;

import com.pbl4.monolingo.dao.NotebookRepository;
import com.pbl4.monolingo.entity.Account;
import com.pbl4.monolingo.entity.Notebook;
import com.pbl4.monolingo.entity.embeddable.AccountLexiconId;
import com.pbl4.monolingo.entity.embeddable.LexiconId;
import com.pbl4.monolingo.service.AccountService;
import com.pbl4.monolingo.service.NotebookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/dictionary")

public class NotebookRestController {

    private final NotebookService notebookService;
    private final AccountService accountService;
    private final NotebookRepository notebookRepository;
    private final BotController botController;

    @Autowired
    public NotebookRestController(NotebookRepository notebookRepository,
                                  NotebookService notebookService,
                                  AccountService accountService,
                                  BotController botController) {
        this.notebookRepository = notebookRepository;
        this.notebookService = notebookService;
        this.accountService = accountService;
        this.botController = botController;
    }

    @GetMapping("/notebook/{accountId}")
    public List<Notebook> getAllNotebooks(@PathVariable Integer accountId) {
        return notebookRepository.findAllByIdAccountId(accountId);
    }

    @PostMapping("/notebook")
    public ResponseEntity<Notebook> createNotebook(@RequestBody Notebook notebook) {
        Notebook createdNotebook = notebookRepository.save(notebook);
        return ResponseEntity.ok(createdNotebook);
    }

    @DeleteMapping("/notebook/{accountId}/{synsetId}/{lexiconNum}")
    public void deleteNotebook(@PathVariable Integer accountId,
                                               @PathVariable BigDecimal synsetId,
                                               @PathVariable BigDecimal lexiconNum) {
        AccountLexiconId id = new AccountLexiconId(accountId, new LexiconId(synsetId, lexiconNum));
        notebookRepository.deleteById(id);
    }

    @PostMapping("/notebook/update")
    public void updateNotebook(@RequestParam String word, @RequestParam boolean isExist, Principal principal) {
        Account account = accountService.getAccountByUsername(principal.getName());
        if (isExist)
            notebookService.deleteNotebook(account.getAccountId(), word);
        else
            notebookService.addNotebook(account.getAccountId(), word);

        botController.updateSentences(account.getAccountId(), 13, false);

    }
}
