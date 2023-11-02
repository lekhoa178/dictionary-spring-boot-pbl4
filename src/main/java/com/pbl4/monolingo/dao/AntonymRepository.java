package com.pbl4.monolingo.dao;

import com.pbl4.monolingo.entity.Antonym;
import com.pbl4.monolingo.entity.Similar;
import com.pbl4.monolingo.entity.embeddable.LexiconRelId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface AntonymRepository extends JpaRepository<Antonym, LexiconRelId> {

    List<Antonym> findByIdLexiconId1SynsetId(BigDecimal synsetId);

}
