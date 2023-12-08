package com.pbl4.monolingo.entity.embeddable;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FriendId implements Serializable {

    private int followerId;
    private int followingId;

    public FriendId(int followerId, int followingId) {
        this.followerId = followerId;
        this.followingId = followingId;
    }

    public FriendId() {

    }

    public int getFollowerId() {
        return followerId;
    }

    public void setFollowerId(int followerId) {
        this.followerId = followerId;
    }

    public int getFollowingId() {
        return followingId;
    }

    public void setFollowingId(int followingId) {
        this.followingId = followingId;
    }

    @Override
    public String toString() {
        return "FriendId{" +
                "followerId=" + followerId +
                ", followingId=" + followingId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FriendId friendId = (FriendId) o;
        return followerId == friendId.followerId && followingId == friendId.followingId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(followerId, followingId);
    }
}
