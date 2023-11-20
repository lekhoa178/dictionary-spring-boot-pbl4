package com.pbl4.monolingo.utility.dto;

import com.pbl4.monolingo.entity.Account;
import lombok.Getter;

@Getter
public class AccountStats {

    private Account account;
    private Long experience;
    private Double onlineHours;
    private int streak;

    public AccountStats() {}

    public AccountStats(Account account, Long experience, Double onlineHours, int streak) {
        this.account = account;
        this.experience = experience;
        this.onlineHours = onlineHours;
        this.streak = streak;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setExperience(Long experience) {
        this.experience = experience;
    }

    public void setOnlineHours(Double onlineHours) {
        this.onlineHours = onlineHours;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }
}
