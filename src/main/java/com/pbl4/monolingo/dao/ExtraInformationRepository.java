package com.pbl4.monolingo.dao;

import com.pbl4.monolingo.entity.ExtraInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExtraInformationRepository extends JpaRepository<ExtraInformation, Integer> {

    ExtraInformation findExtraInformationByAccountId(Integer id);
}
