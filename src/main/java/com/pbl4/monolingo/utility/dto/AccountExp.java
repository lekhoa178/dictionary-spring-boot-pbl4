package com.pbl4.monolingo.utility.dto;

import com.pbl4.monolingo.entity.Account;

public class AccountExp {

    private Account account;
    private Long experience;

    public AccountExp() {}

    public AccountExp(Account account, Long experience) {
        this.account = account;
        this.experience = experience;
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

    @Override
    public String toString() {
        return "AccountExp{" +
                "account=" + account +
                ", experience=" + experience +
                '}';
    }
}
