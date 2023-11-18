package com.pbl4.monolingo.utility.dto;

import jakarta.persistence.Column;

import java.util.List;

public class StageLevelState {

    private Integer stageId;
    private String title;
    private String depiction;
    private List<LevelState> levels;

    public StageLevelState() {}

    public StageLevelState(Integer stageId, String title, String depiction, List<LevelState> levels) {
        this.stageId = stageId;
        this.title = title;
        this.depiction = depiction;
        this.levels = levels;
    }

    public Integer getStageId() {
        return stageId;
    }

    public void setStageId(Integer stageId) {
        this.stageId = stageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDepiction() {
        return depiction;
    }

    public void setDepiction(String depiction) {
        this.depiction = depiction;
    }

    public List<LevelState> getLevels() {
        return levels;
    }

    public void setLevels(List<LevelState> levels) {
        this.levels = levels;
    }

    @Override
    public String toString() {
        return "StageLevelState{" +
                "stageId=" + stageId +
                ", title='" + title + '\'' +
                ", depiction='" + depiction + '\'' +
                ", levels=" + levels +
                '}';
    }
}
