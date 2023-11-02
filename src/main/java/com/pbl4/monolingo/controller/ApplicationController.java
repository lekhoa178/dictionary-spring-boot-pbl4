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

    @Autowired
    public ApplicationController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    // add default endpoint

    @GetMapping("/")
    public String showMain(Model theModel) {

//        List<Lexicon> result = dictionaryService.searchByWord("hel");
//        for (Lexicon lex : result) {
//            System.out.println(lex.getWord());
//        }


        return "main.html";
    }

    @GetMapping("/test")
    public String showMainTest(Model theModel) {

        return "main.html";
    }

}
