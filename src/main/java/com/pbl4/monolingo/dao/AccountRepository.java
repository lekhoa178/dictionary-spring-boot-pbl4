package com.pbl4.monolingo.dao;

import com.pbl4.monolingo.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findAccountByUsername(String username);
    Optional<Account> findByUsername(String username);

    boolean existsAccountByUsernameAndPassword(String username,String password);

    @Query("select p from Account p where p.username like %?1%")
    List<Account> searchAccount(String keyword);
}
