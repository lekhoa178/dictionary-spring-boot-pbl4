package com.pbl4.monolingo.entity;

import com.pbl4.monolingo.entity.embeddable.FriendId;
import com.pbl4.monolingo.entity.embeddable.LexiconId;
import jakarta.persistence.*;

@Entity
@Table(name = "friend")
public class Friend {

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "followerId", column = @Column(name = "follower_id")),
            @AttributeOverride(name = "followingId", column = @Column(name = "following_id"))
    })
    private FriendId id;

    @ManyToOne
    @MapsId("followerId")
    @JoinColumn(name = "follower_id",insertable = false, updatable = false)
    private Account follower;

    @ManyToOne()
    @MapsId("followingId")
    @JoinColumn(name = "following_id",insertable = false, updatable = false)
    private Account following;
    @Column
    private Boolean hasMessage = false;

    public Friend(FriendId id, Account follower, Account following, Boolean hasMessage) {
        this.id = id;
        this.follower = follower;
        this.following = following;
        this.hasMessage = hasMessage;
    }

    public Boolean getHasMessage() {
        return hasMessage;
    }

    public void setHasMessage(Boolean hasMessage) {
        this.hasMessage = hasMessage;
    }

    public Friend(FriendId id, Account follower, Account following) {
        this.id = id;
        this.follower = follower;
        this.following = following;
    }

    public Friend() {

    }

    public FriendId getId() {
        return id;
    }

    public void setId(FriendId id) {
        this.id = id;
    }

    public Account getFollower() {
        return follower;
    }

    public void setFollower(Account follower) {
        this.follower = follower;
    }

    public Account getFollowing() {
        return following;
    }

    public void setFollowing(Account following) {
        this.following = following;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "id=" + id +
                ", follower=" + follower +
                ", following=" + following +
                '}';
    }
}
