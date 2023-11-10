package com.pbl4.monolingo.dao;

import com.pbl4.monolingo.entity.Notebook;
import com.pbl4.monolingo.entity.Synset;
import com.pbl4.monolingo.entity.embeddable.AccountLexiconId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotebookRepository extends JpaRepository<Notebook, AccountLexiconId> {
    void deleteByAccount_AccountIdAndLexicon_Word(int accountId, String word);
    Notebook findNotebookByAccount_AccountIdAndLexicon_Word(int accountId, String word);
}
