package com.pbl4.monolingo.dao;

import com.pbl4.monolingo.entity.Account;
import com.pbl4.monolingo.entity.DailyMission;
import com.pbl4.monolingo.entity.Mission;
import com.pbl4.monolingo.entity.embeddable.DailyMissionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DailyMissionRepository extends JpaRepository<DailyMission, DailyMissionId> {
    List<DailyMission> getAllByIdDayIdAndIdAccountId(int dayId, int accountId);
}
