package com.pbl4.monolingo.rest;

import com.pbl4.monolingo.entity.Lexicon;
import com.pbl4.monolingo.service.LexiconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/dictionary")
public class LexiconRestController {
    @Autowired
    private LexiconService lexiconService;
    @GetMapping("/getLexiconById/{synsetId}/{lexiconId}")
    public Lexicon lexiconList(@PathVariable BigDecimal synsetId, @PathVariable BigDecimal lexiconId){
        return lexiconService.getById(synsetId,lexiconId);
    }
}
