package com.pbl4.monolingo.entity;

import com.pbl4.monolingo.entity.embeddable.LevelId;
import jakarta.persistence.*;

@Entity
@Table(name = "level")
public class Level {

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "stageId", column = @Column(name = "stage_id")),
            @AttributeOverride(name = "levelId", column = @Column(name = "level_id"))
    })
    private LevelId id;

    @Column(name = "complete")
    private boolean complete;

    public Level() {}

    public Level(LevelId id, boolean complete) {
        this.id = id;
        this.complete = complete;
    }

    public LevelId getId() {
        return id;
    }

    public void setId(LevelId id) {
        this.id = id;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    @Override
    public String toString() {
        return "Level{" +
                "id=" + id +
                ", complete=" + complete +
                '}';
    }
}
