package com.pbl4.monolingo.dao;

import com.pbl4.monolingo.entity.Level;
import com.pbl4.monolingo.entity.embeddable.LevelId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LevelRepository extends JpaRepository<Level, LevelId> {
    List<Level> findById_StageId(int id);
}
