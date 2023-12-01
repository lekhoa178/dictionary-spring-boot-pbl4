package com.pbl4.monolingo.dao;

import com.pbl4.monolingo.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface MissionRepository extends JpaRepository<Mission,Integer> {
}
