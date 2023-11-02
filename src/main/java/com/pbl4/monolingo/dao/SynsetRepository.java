package com.pbl4.monolingo.dao;

import com.pbl4.monolingo.entity.HistoryResearch;
import com.pbl4.monolingo.entity.Synset;
import com.pbl4.monolingo.entity.embeddable.AccountLexiconId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface SynsetRepository extends JpaRepository<Synset, BigDecimal> {
}
