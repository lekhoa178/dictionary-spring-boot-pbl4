package com.pbl4.monolingo.dao;

import com.pbl4.monolingo.entity.Lexicon;
import com.pbl4.monolingo.entity.embeddable.LexiconId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface LexiconRepository extends JpaRepository<Lexicon, LexiconId> {

    List<Lexicon> findByWord(String word);

    List<Lexicon> findByWordStartsWith(String word);

    List<Lexicon> findByIdSynsetId(BigDecimal synsetId);

}
