package com.pbl4.monolingo.controller;

import com.pbl4.monolingo.service.NLTKService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NLTKController {
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
