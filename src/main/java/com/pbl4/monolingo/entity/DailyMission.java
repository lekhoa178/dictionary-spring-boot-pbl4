package com.pbl4.monolingo.entity;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "daily_mission")
public class DailyMission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "mission_id")
    private Mission mission;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    public DailyMission(Long id, Mission mission, Account account) {
        this.id = id;
        this.mission = mission;
        this.account = account;
    }

    public DailyMission(Long id, Mission mission, Account account, Date date) {
        this.id = id;
        this.mission = mission;
        this.account = account;
        this.date = date;
    }

    public DailyMission(Mission mission, Account account, Date date) {
        this.mission = mission;
        this.account = account;
        this.date = date;
    }

    public DailyMission() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
