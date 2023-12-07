package com.pbl4.monolingo.utility.dto;

import com.pbl4.monolingo.entity.Friend;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FriendExp {

    private Friend friend;
    private int exp;

    public FriendExp() {}

    public Friend getFriend() {
        return friend;
    }

    public void setFriend(Friend friend) {
        this.friend = friend;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "FriendExp{" +
                "friend=" + friend +
                ", exp=" + exp +
                '}';
    }
}
