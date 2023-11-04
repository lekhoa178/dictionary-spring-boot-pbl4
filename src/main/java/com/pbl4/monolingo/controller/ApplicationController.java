package com.pbl4.monolingo.controller;

import com.pbl4.monolingo.dao.*;
import com.pbl4.monolingo.entity.*;
import com.pbl4.monolingo.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ApplicationController {

    private DictionaryService dictionaryService;
    private StageRepository stageRepository;

    @Autowired
    public ApplicationController(DictionaryService dictionaryService, StageRepository stageRepository) {
        this.dictionaryService = dictionaryService;
        this.stageRepository = stageRepository;
    }

    // add default endpoint

    @GetMapping("/")
    public String showMain(Model model) {

        return "redirect:/learn";
    }

    @GetMapping("/learn")
    public String showMainTest(Model model) {
        model.addAttribute("subPage", "/sub/learn.html");
        model.addAttribute("stages", stageRepository.findAll());
        return "main.html";
    }


}
