package com.pbl4.monolingo.service;

import com.pbl4.monolingo.entity.Account;

public interface ExtraInfoService {
    public void loseHeart(int accountId);

    public void restoreHeart(int accountId);

    public void updateExtraInfo(Account account);
}
