package com.pbl4.monolingo.utility.dto;

public class LevelState {

    private Integer levelId;
    private boolean complete;

    public LevelState() {}

    public LevelState(Integer levelId, boolean complete) {
        this.levelId = levelId;
        this.complete = complete;
    }


    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    @Override
    public String toString() {
        return "LevelState{" +
                ", levelId=" + levelId +
                ", complete=" + complete +
                '}';
    }
}
