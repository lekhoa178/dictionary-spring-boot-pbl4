package com.pbl4.monolingo.dao;

import com.pbl4.monolingo.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MissionRepository extends JpaRepository<Mission, Integer> {

    @Query(value = "SELECT * FROM Mission AS m ORDER BY RAND() LIMIT :amount", nativeQuery = true)
    List<Mission> findRandomMission(int amount);

//    @Query(value = "SELECT DISTINCT Mission.type FROM Mission AS T")
//    List<Integer> findAllType();
}
