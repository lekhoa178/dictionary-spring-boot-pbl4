package com.pbl4.monolingo.utility.dto;

public class OnlineStatusDTO {
    private boolean online;

    public OnlineStatusDTO(boolean online) {
        this.online = online;
    }

    public OnlineStatusDTO() {
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
}
