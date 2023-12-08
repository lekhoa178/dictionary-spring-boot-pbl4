package com.pbl4.monolingo.utility.dto;

public class SearchFriend {

    private Integer accountId;
    private String username;
    private String name;
    private boolean friended;

    public SearchFriend(Integer accountId, String username, String name, boolean friended) {
        this.accountId = accountId;
        this.username = username;
        this.name = name;
        this.friended = friended;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFriended() {
        return friended;
    }

    public void setFriended(boolean friended) {
        this.friended = friended;
    }

    @Override
    public String toString() {
        return "SearchAccount{" +
                "accountId=" + accountId +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", friended=" + friended +
                '}';
    }
}
