package com.pbl4.monolingo.service;

import com.pbl4.monolingo.entity.Synset;

import java.math.BigDecimal;

public interface SynsetService {
    Synset findById(BigDecimal synsetId);
}
