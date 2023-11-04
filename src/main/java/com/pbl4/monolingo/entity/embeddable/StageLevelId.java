package com.pbl4.monolingo.entity.embeddable;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class StageLevelId implements Serializable {

    private Integer stageId;

    private Integer levelId;

    public StageLevelId() {}

    public StageLevelId(Integer stageId, Integer levelId) {
        this.stageId = stageId;
        this.levelId = levelId;
    }

    public Integer getStageId() {
        return stageId;
    }

    public void setStageId(Integer stageId) {
        this.stageId = stageId;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    @Override
    public String toString() {
        return "StageLevelId{" +
                "stageId=" + stageId +
                ", levelId=" + levelId +
                '}';
    }
}
