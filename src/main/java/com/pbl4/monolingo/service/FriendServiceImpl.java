package com.pbl4.monolingo.service;

import com.pbl4.monolingo.dao.AccountRepository;
import com.pbl4.monolingo.dao.DataPerDayRepository;
import com.pbl4.monolingo.dao.ExtraInformationRepository;
import com.pbl4.monolingo.dao.FriendRepository;
import com.pbl4.monolingo.entity.Account;
import com.pbl4.monolingo.entity.Friend;
import com.pbl4.monolingo.entity.embeddable.FriendId;
import com.pbl4.monolingo.utility.dto.AccountExp;
import com.pbl4.monolingo.utility.dto.AccountStats;
import com.pbl4.monolingo.utility.dto.FriendExp;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FriendServiceImpl implements FriendService {

    private final FriendRepository friendRepository;
    private final AccountRepository accountRepository;
    private final ExtraInformationRepository extraInformationRepository;
    private final DataPerDayRepository dataPerDayRepository;

    @Override
    public List<Friend> getFollowers(int followingId) {
        return friendRepository.findAllByIdFollowingId(followingId);
    }

    @Override
    public List<Friend> getFollowings(int followerId) {
        return friendRepository.findAllByIdFollowerId(followerId);
    }

    @Override
    public void follow(int followerId, int followingId) {
        Optional<Account> following = accountRepository.findById(followingId);
        Optional<Account> follower = accountRepository.findById(followerId);

        if (following.isPresent() && follower.isPresent()) {
            Friend friend = new Friend(new FriendId(followerId, followingId), follower.get(), following.get());
            friendRepository.save(friend);
        }
    }

    @Override
    public void unFollow(int followerId, int followingId) {
        friendRepository.deleteById(new FriendId(followerId, followingId));
    }

    @Override
    public List<FriendExp> getFollowingExps(int followerId) {
        List<FriendExp> result = new ArrayList<>();
        List<Friend> friends = getFollowings(followerId);

        for (Friend friend : friends) {
            result.add(new FriendExp(friend, Math.toIntExact(getAccountExp(friend.getId().getFollowingId()).getExperience())));
        }

        return result;
    }

    private AccountExp getAccountExp(int accountId) {
        List<Object[]> results = dataPerDayRepository.findAccountBySumStats(accountId);
        if (results.isEmpty())
            return new AccountExp(accountRepository.findById(accountId).get(), 0L);
        return new AccountExp(
                (Account)results.get(0)[0],
                (Long)results.get(0)[1]);
    }
}
