package com.pbl4.monolingo.utility.dto;

import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class AccountDPDStat {

    private List<Integer> experiences;
    private List<Integer> onlineHours;
    private List<String> days;

    public AccountDPDStat() {}

    public AccountDPDStat(List<Integer> experiences, List<Integer> onlineHours, List<String> days) {
        this.experiences = experiences;
        this.onlineHours = onlineHours;
        this.days = days;
    }

    public void setExperiences(List<Integer> experiences) {
        this.experiences = experiences;
    }

    public void setOnlineHours(List<Integer> onlineHours) {
        this.onlineHours = onlineHours;
    }

    public void setDays(List<String> days) {
        this.days = days;
    }

    public List<Integer> getExperiences() {
        return experiences;
    }

    public List<Integer> getOnlineHours() {
        return onlineHours;
    }

    public List<String> getDays() {
        return days;
    }
}
