package com.pbl4.monolingo.entity.embeddable;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable

public class LevelFulfillId implements Serializable {

    private LevelId levelId;
    private Integer accountId;

    public LevelFulfillId() {}

    public LevelFulfillId(LevelId levelId, Integer accountId) {
        this.levelId = levelId;
        this.accountId = accountId;
    }

    public LevelId getLevelId() {
        return levelId;
    }

    public void setLevelId(LevelId levelId) {
        this.levelId = levelId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LevelFulfillId that = (LevelFulfillId) o;
        return Objects.equals(levelId, that.levelId) && Objects.equals(accountId, that.accountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(levelId, accountId);
    }
}
