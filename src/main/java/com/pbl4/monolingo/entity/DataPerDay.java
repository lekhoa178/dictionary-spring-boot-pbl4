package com.pbl4.monolingo.entity;

import com.pbl4.monolingo.entity.embeddable.DataPerDayId;
import jakarta.persistence.*;

@Entity
@Table(name = "data_per_day")
public class DataPerDay {

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "dayId", column = @Column(name = "day_id")),
            @AttributeOverride(name = "accountId", column = @Column(name = "account_id"))
    })
    private DataPerDayId id;

    @ManyToOne
    @MapsId("accountId")
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "experience")
    private Integer experience;

    @Column(name = "online_hours")
    private Float onlineHours;

    public DataPerDay() {}

    public DataPerDay(DataPerDayId id, Integer experience, Float onlineHours) {
        this.id = id;
        this.experience = experience;
        this.onlineHours = onlineHours;
    }

    public DataPerDay(DataPerDayId id, Account account, Integer experience, Float onlineHours) {
        this.id = id;
        this.account = account;
        this.experience = experience;
        this.onlineHours = onlineHours;
    }

    public DataPerDayId getId() {
        return id;
    }

    public void setId(DataPerDayId id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Float getOnlineHours() {
        return onlineHours;
    }

    public void setOnlineHours(Float onlineHours) {
        this.onlineHours = onlineHours;
    }

    @Override
    public String toString() {
        return "DataPerDay{" +
                "id=" + id +
                ", account=" + account +
                ", experience=" + experience +
                ", onlineHours=" + onlineHours +
                '}';
    }
}
