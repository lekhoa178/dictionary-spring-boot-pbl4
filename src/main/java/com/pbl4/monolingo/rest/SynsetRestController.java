package com.pbl4.monolingo.rest;

import com.pbl4.monolingo.entity.Synset;
import com.pbl4.monolingo.service.SynsetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/dictionary")

public class SynsetRestController {
    @Autowired
    private SynsetService service;
    @GetMapping("/getSynsetById/{synsetId}")
    public Synset getById(@PathVariable BigDecimal synsetId){

        return service.findById(synsetId);
    }
}
