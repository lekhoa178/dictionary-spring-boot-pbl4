package com.pbl4.monolingo.dao;

import com.pbl4.monolingo.entity.Level;
import com.pbl4.monolingo.entity.embeddable.StageLevelId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LevelRepository extends JpaRepository<Level, StageLevelId> {
}
