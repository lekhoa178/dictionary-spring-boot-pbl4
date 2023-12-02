package com.pbl4.monolingo.service;

import com.pbl4.monolingo.entity.Account;

public interface ExtraInfoService {
    void loseHeart(int accountId);

    void restoreHeart(int accountId);

    void updateExtraInfo(Account account);

    void buyHearts(int accountId);

    void updateCoin(int accountId, int offset);
}
