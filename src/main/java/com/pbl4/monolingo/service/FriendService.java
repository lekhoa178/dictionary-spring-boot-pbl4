package com.pbl4.monolingo.service;

import com.pbl4.monolingo.entity.Friend;
import com.pbl4.monolingo.utility.dto.FriendDetail;

import java.util.List;

public interface FriendService {

    List<Friend> getFollowers(int followingId);

    List<Friend> getFollowings(int followerId);

    void follow(int followerId, int followingId);

    void unFollow(int followerId, int followingId);

    List<FriendDetail> getFollowingExps(int followerId);

}
