package com.pbl4.monolingo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "stage")
public class Stage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stage_id")
    private Integer stageId;

    @Column(name = "title")
    @NotNull(message = "Không được bỏ trống mục này")
    @Size(min = 1, message = "Không được bỏ trống mục này")
    private String title;

    @Column(name = "depiction")
    @NotNull(message = "Không được bỏ trống mục này")
    @Size(min = 1, message = "Không được bỏ trống mục này")
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
