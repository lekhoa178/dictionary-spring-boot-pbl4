package com.pbl4.monolingo.dao;

import com.pbl4.monolingo.entity.Notebook;
import com.pbl4.monolingo.entity.embeddable.AccountLexiconId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotebookRepository extends JpaRepository<Notebook, AccountLexiconId> {

    List<Notebook> findAllByIdAccountId(Integer accountId);

}
