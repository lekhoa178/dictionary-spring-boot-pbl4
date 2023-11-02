package com.pbl4.monolingo.dao;

import com.pbl4.monolingo.entity.SeeAlso;
import com.pbl4.monolingo.entity.embeddable.LexiconRelId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeeAlsoRepository extends JpaRepository<SeeAlso, LexiconRelId> {
}
