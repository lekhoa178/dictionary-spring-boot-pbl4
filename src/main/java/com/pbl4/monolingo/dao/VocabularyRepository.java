package com.pbl4.monolingo.dao;

import com.pbl4.monolingo.entity.Vocabulary;
import com.pbl4.monolingo.entity.embeddable.LevelId;
import com.pbl4.monolingo.entity.embeddable.VocabularyId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VocabularyRepository extends JpaRepository<Vocabulary, VocabularyId> {

    List<Vocabulary> findAllByIdLevelId(LevelId id);

    @Query("SELECT MAX(v.id.vocabularyNum) from Vocabulary v")
    int findMaxId();
}
