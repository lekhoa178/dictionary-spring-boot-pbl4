package com.pbl4.monolingo.rest;

import com.pbl4.monolingo.service.NLTKService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class NTLKRestController {

    private final NLTKService nltkService;

    @Autowired
    public NTLKRestController(NLTKService nltkService) {
        this.nltkService = nltkService;
    }

    @GetMapping("/cfg/sentence")
    public String tokenizeText() {
        return nltkService.CFG();
    }

    @GetMapping("/cfg/sentences/{amount}")
    public List<String> getSeteneces(@PathVariable int amount) {
        List<String> results = new ArrayList<>();
        for (int i = 0; i < amount; ++i) {
            results.add(nltkService.CFG());
        }

        return results;
    }

}
