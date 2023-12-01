package com.pbl4.monolingo.dao;

import com.pbl4.monolingo.entity.Account;
import com.pbl4.monolingo.entity.DailyMission;
import com.pbl4.monolingo.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface DailyMissionRepository extends JpaRepository<DailyMission,Integer> {
    @Query(value ="SELECT mission from DailyMission where account.accountId = :account_id and date = :date" )
    List<Mission> getDailyMissionByAccountAndDate(int account_id, Date date);

}
