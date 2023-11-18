package com.pbl4.monolingo.dao;

import com.pbl4.monolingo.entity.LevelFulfill;
import com.pbl4.monolingo.entity.embeddable.LevelFulfillId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LevelFulfillRepository extends JpaRepository<LevelFulfill, LevelFulfillId> {
}
