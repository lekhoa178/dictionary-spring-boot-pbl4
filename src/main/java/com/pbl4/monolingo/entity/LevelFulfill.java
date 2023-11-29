package com.pbl4.monolingo.entity;

import com.pbl4.monolingo.entity.embeddable.LevelFulfillId;
import jakarta.persistence.*;

@Entity
@Table(name = "level_fulfill")
public class LevelFulfill {

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "levelId.stageId", column = @Column(name = "stage_id")),
            @AttributeOverride(name = "levelId.levelId", column = @Column(name = "level_id")),
            @AttributeOverride(name = "accountId", column = @Column(name = "account_id")),
    })
    private LevelFulfillId id;

    public LevelFulfill() {}

    public LevelFulfill(LevelFulfillId id) {
        this.id = id;
    }

    public LevelFulfillId getId() {
        return id;
    }

    public void setId(LevelFulfillId id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "LevelState{" +
                "id=" + id +
                '}';
    }
}
