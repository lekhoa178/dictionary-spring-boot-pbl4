package com.pbl4.monolingo.entity.embeddable;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPacketId that = (UserPacketId) o;
        return Objects.equals(packetId, that.packetId) && Objects.equals(accountId, that.accountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(packetId, accountId);
    }
}
