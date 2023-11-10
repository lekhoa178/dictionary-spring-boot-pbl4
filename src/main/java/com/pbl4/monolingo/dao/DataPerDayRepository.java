package com.pbl4.monolingo.dao;

import com.pbl4.monolingo.entity.DataPerDay;
import com.pbl4.monolingo.entity.embeddable.DataPerDayId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataPerDayRepository extends JpaRepository<DataPerDay, DataPerDayId> {

}
