package com.pbl4.monolingo.controller;

import com.pbl4.monolingo.dao.*;
import com.pbl4.monolingo.entity.*;
import com.pbl4.monolingo.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.ArrayList;
import java.util.Arrays;
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
        //return "main.html";
    }

    @GetMapping("/learn")
    public String showLearn(Model model, @RequestHeader(value = "request-source", required = false) String requestSource) {
        model.addAttribute("stageColors", StageColors);
        model.addAttribute("stages", stageRepository.findAll());
        if (requestSource == null)
            return "main.html";
        else
            return "fragments/learn";
    }

    @GetMapping("/practice")

    public String showPratice(Model model, @RequestHeader(value = "request-source", required = false) String requestSource) {
        if (requestSource == null)
            return "main.html";
        else
            return "fragments/practice";
    }

    @GetMapping("/rank")

    public String showRank(Model model, @RequestHeader(value = "request-source", required = false) String requestSource) {
        if (requestSource == null)
            return "main.html";
        else
            return "fragments/rank";
    }

    @GetMapping("/mission")

    public String showMission(Model model, @RequestHeader(value = "request-source", required = false) String requestSource) {
        if (requestSource == null)
            return "main.html";
        else
            return "fragments/mission";
    }

    @GetMapping("/store")

    public String showStore(Model model, @RequestHeader(value = "request-source", required = false) String requestSource) {
        if (requestSource == null)
            return "main.html";
        else
            return "fragments/store";
    }


    private List<String> StageColors = new ArrayList<>(Arrays.asList(
            "#58cc02",
            "#ce82ff",
            "#00cd9c",
            "#1cb0f6",
            "#ff86d0",
            "#ff9600",
            "#cc348d"
    ));
}
