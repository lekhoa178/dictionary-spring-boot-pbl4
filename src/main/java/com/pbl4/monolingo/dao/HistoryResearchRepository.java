package com.pbl4.monolingo.dao;

import com.pbl4.monolingo.entity.HistoryResearch;
import com.pbl4.monolingo.entity.embeddable.AccountLexiconId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryResearchRepository extends JpaRepository<HistoryResearch, AccountLexiconId> {
}
