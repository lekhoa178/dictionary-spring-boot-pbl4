package com.pbl4.monolingo.entity;

import com.pbl4.monolingo.entity.embeddable.DailyMissionId;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "daily_mission")
public class DailyMission {
    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "dayId", column = @Column(name = "day_id")),
            @AttributeOverride(name = "accountId", column = @Column(name = "account_id")),
            @AttributeOverride(name = "missionId", column = @Column(name = "mission_id"))
    })
    private DailyMissionId id;

    @ManyToOne
    @MapsId("missionId")
    @JoinColumn(name = "mission_id")
    private Mission mission;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("accountId")
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "progress")
    private Double progress;

    public DailyMission() {}

    public DailyMission(DailyMissionId id, Mission mission, Account account, Double progress) {
        this.id = id;
        this.mission = mission;
        this.account = account;
        this.progress = progress;
    }

    public DailyMissionId getId() {
        return id;
    }

    public void setId(DailyMissionId id) {
        this.id = id;
    }

    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Double getProgress() {
        return progress;
    }

    public void setProgress(Double progress) {
        this.progress = progress;
    }

    @Override
    public String toString() {
        return "DailyMission{" +
                "id=" + id +
                ", mission=" + mission +
                ", account=" + account +
                ", progress=" + progress +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DailyMission that = (DailyMission) o;
        return Objects.equals(id, that.id) && Objects.equals(mission, that.mission) && Objects.equals(account, that.account) && Objects.equals(progress, that.progress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mission, account, progress);
    }
}
