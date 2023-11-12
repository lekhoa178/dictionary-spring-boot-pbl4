package com.pbl4.monolingo.entity.embeddable;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class DataPerDayId implements Serializable {

    private Integer dayId;

    private Integer accountId;

    public DataPerDayId() {}

    public DataPerDayId(Integer dayId, Integer accountId) {
        this.dayId = dayId;
        this.accountId = accountId;
    }

    public Integer getDayId() {
        return dayId;
    }

    public void setDayId(Integer dayId) {
        this.dayId = dayId;
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
        DataPerDayId that = (DataPerDayId) o;
        return Objects.equals(dayId, that.dayId) && Objects.equals(accountId, that.accountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dayId, accountId);
    }
}
