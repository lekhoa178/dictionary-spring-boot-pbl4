package com.pbl4.monolingo.entity.embeddable;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class UserPacketId implements Serializable {

    private Integer packetId;

    private Integer accountId;

    public UserPacketId() {}

    public UserPacketId(Integer packetId, Integer accountId) {
        this.packetId = packetId;
        this.accountId = accountId;
    }

    public Integer getPacketId() {
        return packetId;
    }

    public void setPacketId(Integer packetId) {
        this.packetId = packetId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }
}
