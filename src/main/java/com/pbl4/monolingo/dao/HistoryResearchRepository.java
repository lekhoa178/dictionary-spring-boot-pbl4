package com.pbl4.monolingo.dao;

import com.pbl4.monolingo.entity.HistoryResearch;
import com.pbl4.monolingo.entity.embeddable.AccountLexiconId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryResearchRepository extends JpaRepository<HistoryResearch, AccountLexiconId> {

    HistoryResearch findByAccount_AccountIdAndLexicon_Word(int accountId, String word);

    List<HistoryResearch> findAllByIdAccountId(int accountId);

    HistoryResearch getFirstByIdAccountId(int accountId);

}
