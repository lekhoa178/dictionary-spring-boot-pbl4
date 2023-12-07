package com.pbl4.monolingo.service;

import com.pbl4.monolingo.entity.Account;

public interface ExtraInfoService {
    public void loseHeart(int accountId);

    public void restoreHeart(int accountId);

    public int updateExtraInfo(Account account);

    public void buyHearts(int accountId);
}
