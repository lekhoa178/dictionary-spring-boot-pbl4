package com.pbl4.monolingo.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "stage")
public class Stage {

    @Id
    @Column(name = "stage_id")
    private Integer stageId;

    @Column(name = "title")
    private String title;

    @Column(name = "depiction")
    private String depiction;

    @OneToMany
    @MapsId("stageId")
    @JoinColumn(name = "stage_id")
    private List<Level> levels;

    public Stage() {}

    public Stage(Integer stageId, String title, String depiction) {
        this.stageId = stageId;
        this.title = title;
        this.depiction = depiction;
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

    public List<Level> getLevels() {
        return levels;
    }

    public void setLevels(List<Level> levels) {
        this.levels = levels;
    }

    @Override
    public String toString() {
        return "Stage{" +
                "stageId=" + stageId +
                ", title='" + title + '\'' +
                ", depiction='" + depiction + '\'' +
                ", levels=" + levels +
                '}';
    }
}
