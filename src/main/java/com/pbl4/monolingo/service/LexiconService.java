package com.pbl4.monolingo.service;

import com.pbl4.monolingo.entity.Lexicon;

import java.math.BigDecimal;

public interface LexiconService {
    Lexicon getById(BigDecimal synsetId, BigDecimal lexiconId);
}
