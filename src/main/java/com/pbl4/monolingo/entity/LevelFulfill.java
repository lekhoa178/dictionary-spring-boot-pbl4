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

    @ManyToOne
    @MapsId("accountId")
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @MapsId("levelId")
    @JoinColumns({
            @JoinColumn(name = "stage_id", referencedColumnName = "stage_id"),
            @JoinColumn(name = "level_id", referencedColumnName = "level_id")
    })
    private Level level;


    public LevelFulfill() {}

    public LevelFulfillId getId() {
        return id;
    }

    public void setId(LevelFulfillId id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "LevelState{" +
                "id=" + id +
                ", account=" + account +
                ", level=" + level +
                '}';
    }
}
