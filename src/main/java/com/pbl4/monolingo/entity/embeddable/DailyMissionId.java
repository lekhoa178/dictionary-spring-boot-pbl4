package com.pbl4.monolingo.entity.embeddable;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class DailyMissionId implements Serializable {

    private Integer dayId;
    private Integer accountId;
    private Integer missionId;

    public DailyMissionId() {}

    public DailyMissionId(Integer dayId, Integer accountId, Integer missionId) {
        this.dayId = dayId;
        this.accountId = accountId;
        this.missionId = missionId;
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

    public Integer getMissionId() {
        return missionId;
    }

    public void setMissionId(Integer missionId) {
        this.missionId = missionId;
    }

    @Override
    public String toString() {
        return "DailyMissionId{" +
                "dayId=" + dayId +
                ", accountId=" + accountId +
                ", missionId=" + missionId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DailyMissionId that = (DailyMissionId) o;
        return Objects.equals(dayId, that.dayId) && Objects.equals(accountId, that.accountId) && Objects.equals(missionId, that.missionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dayId, accountId, missionId);
    }
}
