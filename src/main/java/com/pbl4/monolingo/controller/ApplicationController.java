package com.pbl4.monolingo.controller;

import com.pbl4.monolingo.dao.*;
import com.pbl4.monolingo.entity.*;
import com.pbl4.monolingo.service.AccountService;
import com.pbl4.monolingo.service.AccountServiceImpl;
import com.pbl4.monolingo.service.DictionaryService;
import com.pbl4.monolingo.utility.uimodel.DefinitionDetailView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.swing.text.html.parser.Entity;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.*;

@Controller
public class ApplicationController {

    private DictionaryService dictionaryService;
    private StageRepository stageRepository;
    private AccountService accountService;

    @Autowired
    public ApplicationController(DictionaryService dictionaryService,
                                 StageRepository stageRepository,
                                 AccountService accountService) {
        this.dictionaryService = dictionaryService;
        this.stageRepository = stageRepository;
        this.accountService = accountService;
    }

    // add default endpoint

    @GetMapping("/")
    public String showMain(RedirectAttributes redirectAttributes) {

        return "redirect:/learn";
        //return "main.html";
    }

    @GetMapping("/learn")
    public String showLearn(Model model, Principal principal,
                            @RequestHeader(value = "request-source", required = false) String requestSource) {

        model.addAttribute("stageColors", StageColors);
        model.addAttribute("stages", stageRepository.findAll());
        if (requestSource == null) {
            if (principal != null) {
                model.addAttribute("userData", accountService.getAccountInfoByUsername(principal.getName()));
            }
            return "main";
        }
        else
            return "fragments/learn";
    }

    @GetMapping("/practice")

    public String showPractice(Model model, Principal principal,
                               @RequestHeader(value = "request-source", required = false) String requestSource) {
        System.out.println(principal.getName());

        if (requestSource == null) {
            if (principal != null) {
                model.addAttribute("userData", accountService.getAccountInfoByUsername(principal.getName()));
            }
            return "main";
        }
        else
            return "fragments/practice";
    }

    @GetMapping("/rank")

    public String showRank(Model model, Principal principal,
                           @RequestHeader(value = "request-source", required = false) String requestSource) {
        if (requestSource == null) {
            if (principal != null) {
                model.addAttribute("userData", accountService.getAccountInfoByUsername(principal.getName()));
            }
            return "main";
        }
        else
            return "fragments/rank";
    }

    @GetMapping("/mission")

    public String showMission(Model model, Principal principal,
                              @RequestHeader(value = "request-source", required = false) String requestSource) {
        if (requestSource == null) {
            if (principal != null) {
                model.addAttribute("userData", accountService.getAccountInfoByUsername(principal.getName()));
            }
            return "main";
        }
        else
            return "fragments/mission";
    }

    @GetMapping("/store")

    public String showStore(Model model, Principal principal,
                            @RequestHeader(value = "request-source", required = false) String requestSource) {
        if (requestSource == null) {
            if (principal != null) {
                model.addAttribute("userData", accountService.getAccountInfoByUsername(principal.getName()));
            }
            return "main";
        }
        else
            return "fragments/store";
    }

    @GetMapping("/lesson")

    public String showLesson(Model model) {
        return "lesson";
    }

    @GetMapping("/meaning/{word}")

    public String showMeaning(Model model,
                              Principal principal,
                              @PathVariable String word,
                              @RequestHeader(value = "request-source", required = false) String requestSource) {

        HashMap<String, List<DefinitionDetailView>> results = dictionaryService.getDefinitionByWord(word);

        model.addAttribute("definitions", results);
        model.addAttribute("word", word.replaceAll("_", " "));

        if (requestSource == null) {
            if (principal != null) {
                model.addAttribute("userData", accountService.getAccountInfoByUsername(principal.getName()));
            }
            return "main";
        }
        else
            return "fragments/meaning";
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
