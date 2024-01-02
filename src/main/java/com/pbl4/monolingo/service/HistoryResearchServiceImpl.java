package com.pbl4.monolingo.service;

import com.pbl4.monolingo.dao.AccountRepository;
import com.pbl4.monolingo.dao.HistoryResearchRepository;
import com.pbl4.monolingo.dao.LexiconRepository;
import com.pbl4.monolingo.entity.Account;
import com.pbl4.monolingo.entity.HistoryResearch;
import com.pbl4.monolingo.entity.Lexicon;
import com.pbl4.monolingo.entity.Notebook;
import com.pbl4.monolingo.entity.embeddable.AccountLexiconId;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class HistoryResearchServiceImpl implements HistoryResearchService {

    private final HistoryResearchRepository historyResearchRepository;
    private final AccountRepository accountRepository;
    private final LexiconRepository lexiconRepository;

    @Override
    public void deleteResearch(int accountId, String word) {
        word = word.replace(' ', '_');
        HistoryResearch historyResearch = historyResearchRepository.findByAccount_AccountIdAndLexicon_Word(accountId, word);
        if (historyResearch != null)
        {
            historyResearchRepository.delete(historyResearch);
        }
    }

    @Override
    public void deleteFirstResearch(int accountId) {
        historyResearchRepository.delete(historyResearchRepository.getFirstByIdAccountId(accountId));
    }

    @Override
    public void addResearch(int accountId, String word) {
        Optional<Account> rs = accountRepository.findById(accountId);
        Account account = rs.get();

        word = word.replace(' ', '_');
        Lexicon lexicon = lexiconRepository.findByWord(word).get(0);
        AccountLexiconId accountLexiconId = new AccountLexiconId(accountId, lexicon.getId());

        HistoryResearch historyResearch = new HistoryResearch(accountLexiconId, account, lexicon);
        historyResearchRepository.save(historyResearch);
    }

    @Override
    public List<HistoryResearch> getAllByAccountId(Integer accountId) {
        return historyResearchRepository.findAllByIdAccountId(accountId);
    }
}
