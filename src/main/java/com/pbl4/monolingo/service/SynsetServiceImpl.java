package com.pbl4.monolingo.service;

import com.pbl4.monolingo.dao.SynsetRepository;
import com.pbl4.monolingo.entity.Synset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
@Service
public class SynsetServiceImpl implements SynsetService{
    @Autowired
    private SynsetRepository synsetRepository;

    @Override
    public Synset findById(BigDecimal synsetId) {
        return synsetRepository.findById(synsetId).get();
    }
}
