package com.pbl4.monolingo.entity.embeddable;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class VocabularyId implements Serializable {

    private Integer stageId;
    private Integer vocabularyNum;

    public VocabularyId() {}

    public VocabularyId(Integer stageId, Integer vocabularyNum) {
        this.stageId = stageId;
        this.vocabularyNum = vocabularyNum;
    }

    public Integer getStageId() {
        return stageId;
    }

    public void setStageId(Integer stageId) {
        this.stageId = stageId;
    }

    public Integer getVocabularyNum() {
        return vocabularyNum;
    }

    public void setVocabularyNum(Integer vocabularyNum) {
        this.vocabularyNum = vocabularyNum;
    }
}
