package com.pbl4.monolingo.dao;

import com.pbl4.monolingo.entity.Account;
import com.pbl4.monolingo.entity.DataPerDay;
import com.pbl4.monolingo.entity.embeddable.DataPerDayId;
import com.pbl4.monolingo.utility.dto.AccountExp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DataPerDayRepository extends JpaRepository<DataPerDay, DataPerDayId> {

    List<DataPerDay> findAllByIdAccountId(Integer accountId);

    @Query ("SELECT a, SUM(d.experience) " +
            "FROM Account a " +
            "JOIN DataPerDay d ON a.accountId = d.id.accountId " +
            "GROUP BY a.accountId " +
            "ORDER BY SUM(d.experience) DESC ")
    List<Object[]> findAllAccountOrderBySumExp();

}
