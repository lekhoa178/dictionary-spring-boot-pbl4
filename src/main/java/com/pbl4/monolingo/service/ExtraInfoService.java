package com.pbl4.monolingo.service;

import com.pbl4.monolingo.entity.Account;
import com.pbl4.monolingo.entity.ExtraInformation;

public interface ExtraInfoService {
    void loseHeart(int accountId);

    void restoreHeart(int accountId);

    public int updateExtraInfo(Account account);

    void buyHearts(int accountId);

    void updateCoin(int accountId, int offset);
}
