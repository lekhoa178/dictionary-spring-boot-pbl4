package com.pbl4.monolingo.dao;

import com.pbl4.monolingo.entity.Lexicon;
import com.pbl4.monolingo.entity.embeddable.LexiconId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface LexiconRepository extends JpaRepository<Lexicon, LexiconId> {


    List<Lexicon> findByWord(String word);

    List<Lexicon> findByWordStartsWith(String word);
    @Query(value = "SELECT Distinct * FROM lexicon e where e.word like :word LIMIT :limit", nativeQuery=true)
    List<Lexicon> findTopNByWordStartsWith(@Param("word") String word, @Param("limit") int limit);

    List<Lexicon> findByIdSynsetId(BigDecimal synsetId);

}
