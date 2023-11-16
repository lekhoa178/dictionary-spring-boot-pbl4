package com.pbl4.monolingo.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "extra_information")
public class ExtraInformation {

    @Id
    @Column(name = "account_id")
    private Integer accountId;

    @Column(name = "balance")
    private Integer balance;

    @Column(name = "login_days")
    private Integer numberOfLoginDay;

    @Column(name = "consecutive_days")
    private Integer numberOfConsecutiveDay;

    @Column(name = "heart")
    private Integer hearts;

    @Column(name = "lost_heart_time")
    private LocalDateTime lostHeartTimes;

    @OneToOne
    @MapsId
    @JoinColumn(name = "account_id")
    private Account account;

    public ExtraInformation() {}

    public LocalDateTime getLostHeartTimes() {
        return lostHeartTimes;
    }

    public void setLostHeartTimes(LocalDateTime lostHeartTimes) {
        this.lostHeartTimes = lostHeartTimes;
    }

    public ExtraInformation(Integer accountId, Integer balance, Integer numberOfLoginDay, Integer numberOfConsecutiveDay, Integer hearts, LocalDateTime lostHeartTimes) {
        this.accountId = accountId;
        this.balance = balance;
        this.numberOfLoginDay = numberOfLoginDay;
        this.numberOfConsecutiveDay = numberOfConsecutiveDay;
        this.hearts = hearts;
        this.lostHeartTimes = lostHeartTimes;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Integer getNumberOfLoginDay() {
        return numberOfLoginDay;
    }

    public void setNumberOfLoginDay(Integer numberOfLoginDay) {
        this.numberOfLoginDay = numberOfLoginDay;
    }

    public Integer getNumberOfConsecutiveDay() {
        return numberOfConsecutiveDay;
    }

    public void setNumberOfConsecutiveDay(Integer numberOfConsecutiveDay) {
        this.numberOfConsecutiveDay = numberOfConsecutiveDay;
    }

    public Integer getHearts() {
        return hearts;
    }

    public void setHearts(Integer hearts) {
        this.hearts = hearts;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "ExtraInformation{" +
//                "accountId=" + accountId +
                ", balance=" + balance +
                ", numberOfLoginDay=" + numberOfLoginDay +
                ", numberOfConsecutiveDay=" + numberOfConsecutiveDay +
                ", hearts=" + hearts +
                ", account=" + account +
                '}';
    }
}
