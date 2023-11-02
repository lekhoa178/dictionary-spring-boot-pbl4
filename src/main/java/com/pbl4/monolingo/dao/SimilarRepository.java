package com.pbl4.monolingo.dao;

import com.pbl4.monolingo.entity.Similar;
import com.pbl4.monolingo.entity.Synset;
import com.pbl4.monolingo.entity.embeddable.SynsetRelId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface SimilarRepository extends JpaRepository<Similar, SynsetRelId> {

    List<Similar> findByIdSynsetId1(BigDecimal synsetId1);

}
