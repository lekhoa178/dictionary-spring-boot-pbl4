package com.pbl4.monolingo.dao;

import com.pbl4.monolingo.entity.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface AccountTypeRepository extends JpaRepository<AccountType, Integer> {
}
