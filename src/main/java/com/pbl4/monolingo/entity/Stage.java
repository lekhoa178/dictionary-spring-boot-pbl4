package com.pbl4.monolingo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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

    public Stage() {}

    public Stage(Integer stageId, String title, String depiction) {
        this.stageId = stageId;
        this.title = title;
        this.depiction = depiction;
    }

    public Integer getStageId() {
        return this.stageId;
    }

    public void setStageId(Integer stageId) {
        this.stageId = stageId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDepiction() {
        return this.depiction;
    }

    public void setDepiction(String depiction) {
        this.depiction = depiction;
    }

    @Override
    public String toString() {
        return "Stage{" +
                "stageId=" + stageId +
                ", title='" + title + '\'' +
                ", depiction='" + depiction + '\'' +
                '}';
    }
}
