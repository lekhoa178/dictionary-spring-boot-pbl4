package com.pbl4.monolingo.service;

import com.pbl4.monolingo.dao.LexiconRepository;
import com.pbl4.monolingo.entity.Lexicon;
import com.pbl4.monolingo.entity.embeddable.LexiconId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class LexiconServiceImpl implements LexiconService{
    @Autowired
    private LexiconRepository lexiconRepository;
    @Override
    public Lexicon getById(BigDecimal synsetId, BigDecimal lexiconId) {
        LexiconId lexiconIdEmbed = new LexiconId(synsetId,lexiconId);
        return lexiconRepository.findById(lexiconIdEmbed).get();
    }
}
