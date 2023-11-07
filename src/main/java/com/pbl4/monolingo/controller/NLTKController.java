package com.pbl4.monolingo.controller;

import com.pbl4.monolingo.service.NLTKService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NLTKController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NLTKController.class);

    private final NLTKService nltkService;
    @Autowired
    public NLTKController(NLTKService nltkService) {
        this.nltkService = nltkService;
    }

    @PostMapping("/tokenize")
    public String tokenizeText() {

        nltkService.CFG();
        return null;
    }

}
