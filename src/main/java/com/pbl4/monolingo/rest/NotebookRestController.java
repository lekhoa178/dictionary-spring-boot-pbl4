package com.pbl4.monolingo.rest;

import com.pbl4.monolingo.dao.NotebookRepository;
import com.pbl4.monolingo.entity.Account;
import com.pbl4.monolingo.entity.Notebook;
import com.pbl4.monolingo.entity.embeddable.AccountLexiconId;
import com.pbl4.monolingo.entity.embeddable.LexiconId;
import com.pbl4.monolingo.service.AccountService;
import com.pbl4.monolingo.service.NotebookService;
import com.pbl4.monolingo.utility.dto.ResponseMessage;
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
    private final BotRestController botRestController;

    @Autowired
    public NotebookRestController(NotebookRepository notebookRepository,
                                  NotebookService notebookService,
                                  AccountService accountService,
                                  BotRestController botRestController) {
        this.notebookRepository = notebookRepository;
        this.notebookService = notebookService;
        this.accountService = accountService;
        this.botRestController = botRestController;
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

        botRestController.updateSentences(account.getAccountId(), 13, false);

    }

    @GetMapping("/notebook/update/{word}/{isExist}/{accountId}")
    public ResponseEntity<ResponseMessage> updateNotebookAcc(@PathVariable String word, @PathVariable boolean isExist, @PathVariable int accountId) {
        if (isExist)
            notebookService.deleteNotebook(accountId, word);
        else
            notebookService.addNotebook(accountId, word);

        return ResponseEntity.ok(new ResponseMessage("Updated Successfully"));
    }
}
