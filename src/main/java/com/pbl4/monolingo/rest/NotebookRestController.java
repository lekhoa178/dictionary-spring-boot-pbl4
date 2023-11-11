package com.pbl4.monolingo.rest;

import com.pbl4.monolingo.dao.NotebookRepository;
import com.pbl4.monolingo.entity.Notebook;
import com.pbl4.monolingo.entity.embeddable.AccountLexiconId;
import com.pbl4.monolingo.entity.embeddable.LexiconId;
import com.pbl4.monolingo.service.NotebookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/dictionary")

public class NotebookRestController {

    private final NotebookRepository notebookRepository;

    @Autowired
    public NotebookRestController(NotebookRepository notebookRepository) {
        this.notebookRepository = notebookRepository;
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
}
