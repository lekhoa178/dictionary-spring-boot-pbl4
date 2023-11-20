package com.pbl4.monolingo.entity.embeddable;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class VocabularyId implements Serializable {

    private LevelId levelId;
    private Integer vocabularyNum;

    public VocabularyId() {}


    public VocabularyId(LevelId levelId, Integer vocabularyNum) {
        this.levelId = levelId;
        this.vocabularyNum = vocabularyNum;
    }

    public LevelId getLevelId() {
        return levelId;
    }

    public void setLevelId(LevelId levelId) {
        this.levelId = levelId;
    }

    public Integer getVocabularyNum() {
        return vocabularyNum;
    }

    public void setVocabularyNum(Integer vocabularyNum) {
        this.vocabularyNum = vocabularyNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VocabularyId that = (VocabularyId) o;
        return Objects.equals(levelId, that.levelId) && Objects.equals(vocabularyNum, that.vocabularyNum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(levelId, vocabularyNum);
    }
}
