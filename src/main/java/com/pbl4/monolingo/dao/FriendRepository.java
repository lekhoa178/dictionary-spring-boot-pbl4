package com.pbl4.monolingo.dao;

import com.pbl4.monolingo.entity.Friend;
import com.pbl4.monolingo.entity.embeddable.FriendId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<Friend, FriendId> {

    List<Friend> findAllByIdFollowerId(int followerId);

    List<Friend> findAllByIdFollowingId(int followingId);

}
