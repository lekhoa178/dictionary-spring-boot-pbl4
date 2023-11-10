package com.pbl4.monolingo.entity.embeddable;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StageLevelId that = (StageLevelId) o;
        return Objects.equals(stageId, that.stageId) && Objects.equals(levelId, that.levelId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stageId, levelId);
    }
}
