package com.pbl4.monolingo.rest;

import com.pbl4.monolingo.entity.Account;
import com.pbl4.monolingo.entity.HistoryResearch;
import com.pbl4.monolingo.entity.Notebook;
import com.pbl4.monolingo.entity.embeddable.AccountLexiconId;
import com.pbl4.monolingo.entity.embeddable.LexiconId;
import com.pbl4.monolingo.service.AccountService;
import com.pbl4.monolingo.service.HistoryResearchService;
import com.pbl4.monolingo.utility.dto.ResponseMessage;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/dictionary")
@AllArgsConstructor
public class HistoryResearchRestController {

    private final HistoryResearchService historyResearchService;
    private final AccountService accountService;


    @GetMapping("/research/{accountId}")
    public List<HistoryResearch> getAllResearches(@PathVariable Integer accountId) {
        return historyResearchService.getAllByAccountId(accountId);
    }

    @PostMapping("/research/update")
    public void updateResearch(@RequestParam String word, @RequestParam boolean add, Principal principal) {
        Account account = accountService.getAccountByUsername(principal.getName());
        if (!add)
            historyResearchService.deleteResearch(account.getAccountId(), word);
        else {
            List<HistoryResearch> researches = historyResearchService.getAllByAccountId(account.getAccountId());

            if (researches.size() >= 10) {
                historyResearchService.deleteFirstResearch(account.getAccountId());
            }
            historyResearchService.addResearch(account.getAccountId(), word);
        }

    }

    @GetMapping("/research/update/{word}/{add}/{accountId}")
    public ResponseEntity<ResponseMessage> updateResearchAcc(@PathVariable String word, @PathVariable boolean add, @PathVariable int accountId) {
        if (!add)
            historyResearchService.deleteResearch(accountId, word);
        else {
            List<HistoryResearch> researches = historyResearchService.getAllByAccountId(accountId);

            if (researches.size() >= 10) {
                historyResearchService.deleteFirstResearch(accountId);
            }
            historyResearchService.addResearch(accountId, word);
        }

        return ResponseEntity.ok(new ResponseMessage("Updated Successfully"));
    }

}
