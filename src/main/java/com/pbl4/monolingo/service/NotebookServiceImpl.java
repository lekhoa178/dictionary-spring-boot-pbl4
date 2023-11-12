package com.pbl4.monolingo.service;

import com.pbl4.monolingo.dao.AccountRepository;
import com.pbl4.monolingo.dao.LexiconRepository;
import com.pbl4.monolingo.dao.NotebookRepository;
import com.pbl4.monolingo.entity.Account;
import com.pbl4.monolingo.entity.Lexicon;
import com.pbl4.monolingo.entity.Notebook;
import com.pbl4.monolingo.entity.embeddable.AccountLexiconId;
import com.pbl4.monolingo.dao.NotebookRepository;
import com.pbl4.monolingo.entity.Notebook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class NotebookServiceImpl implements NotebookService{
    private final NotebookRepository notebookRepository;
    private final AccountRepository accountRepository;
    private final LexiconRepository lexiconRepository;
    @Autowired
    public NotebookServiceImpl(NotebookRepository notebookRepository, AccountRepository accountRepository, LexiconRepository lexiconRepository) {
        this.notebookRepository = notebookRepository;
        this.accountRepository = accountRepository;
        this.lexiconRepository = lexiconRepository;
    }

    @Override
    public Boolean checkIsExsitInNotebook(int accountId, String word) {
        Notebook notebook = notebookRepository.findNotebookByAccount_AccountIdAndLexicon_Word(accountId, word);
        return notebook != null;
    }

    @Override
    public void deleteNotebook(int accountId, String word) {
        word = word.replace(' ', '_');
        Notebook notebook = notebookRepository.findNotebookByAccount_AccountIdAndLexicon_Word(accountId, word);
        if (notebook != null)
        {
            notebookRepository.delete(notebook);
        }
    }
    @Override
    public void addNotebook(int accountId, String word) {
        Optional<Account> rs = accountRepository.findById(accountId);
        Account account = rs.get();

        word = word.replace(' ', '_');
        Lexicon lexicon = lexiconRepository.findByWord(word).get(0);
        AccountLexiconId accountLexiconId = new AccountLexiconId(accountId, lexicon.getId());

        Notebook notebook = new Notebook(accountLexiconId, account, lexicon);
        notebookRepository.save(notebook);
    }

    @Override
    public List<Notebook> findAll() {
        return notebookRepository.findAll();
    }

    @Override
    public List<Notebook> getAllNotebooksByAccountId(Integer accountId) {
        return null;
    }
}
