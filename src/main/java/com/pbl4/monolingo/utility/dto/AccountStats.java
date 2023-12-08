package com.pbl4.monolingo.utility.dto;

import com.pbl4.monolingo.entity.Account;
import lombok.Getter;

@Getter
public class AccountStats {

    private Account account;
    private Long experience;
    private Double onlineHours;
    private int streak;
    private boolean followed;

    public AccountStats() {}

    public AccountStats(Account account, Long experience, Double onlineHours, int streak, boolean followed) {
        this.account = account;
        this.experience = experience;
        this.onlineHours = onlineHours;
        this.streak = streak;
        this.followed = followed;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Long getExperience() {
        return experience;
    }

    public void setExperience(Long experience) {
        this.experience = experience;
    }

    public Double getOnlineHours() {
        return onlineHours;
    }

    public void setOnlineHours(Double onlineHours) {
        this.onlineHours = onlineHours;
    }

    public int getStreak() {
        return streak;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }

    public boolean isFollowed() {
        return followed;
    }

    public void setFollowed(boolean followed) {
        this.followed = followed;
    }

    @Override
    public String toString() {
        return "AccountStats{" +
                "account=" + account +
                ", experience=" + experience +
                ", onlineHours=" + onlineHours +
                ", streak=" + streak +
                ", followed=" + followed +
                '}';
    }
}
