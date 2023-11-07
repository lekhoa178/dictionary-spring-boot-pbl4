package com.pbl4.monolingo.controller;

import com.pbl4.monolingo.dao.*;
import com.pbl4.monolingo.entity.*;
import com.pbl4.monolingo.service.AccountService;
import com.pbl4.monolingo.service.AccountServiceImpl;
import com.pbl4.monolingo.service.DictionaryService;
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
            return "main.html";
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
            return "main.html";
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
            return "main.html";
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
            return "main.html";
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
            return "main.html";
        }
        else
            return "fragments/store";
    }

    @GetMapping("/lesson")

    public String showLesson(Model model) {
        return "lesson.html";
    }

    @GetMapping("/meaning/{word}")

    public String showMeaning(Model model,
                              Principal principal,
                              @PathVariable String word,
                              @RequestHeader(value = "request-source", required = false) String requestSource) {
        HashMap<String, List<String>> defMap = new HashMap<>();
        defMap.put("DANH TỪ", new ArrayList<String>());
        defMap.put("ĐỘNG TỪ", new ArrayList<String>());
        defMap.put("TÍNH TỪ", new ArrayList<String>());
        defMap.put("TRẠNG TỪ", new ArrayList<String>());

        List<Synset> synsets = dictionaryService.getSynsetsByWord(word);

        for (Synset syn : synsets) {
            List<String> def = defMap.get("ADVERB");
            switch ((int) Math.floor(syn.getSynsetId().divide(new BigDecimal(100000000)).intValue())) {
                case 1:
                    def = defMap.get("DANH TỪ");
                    break;
                case 2:
                    def = defMap.get("ĐỘNG TỪ");
                    break;
                case 3:
                    def = defMap.get("TÍNH TỪ");
                    break;
                case 4:
                    def = defMap.get("TRẠNG TỪ");
                    break;
            }

            def.add(syn.getDefinition());

        }

        HashMap<String, HashMap<String, List<String>>> results = new HashMap<>();

        for (Map.Entry<String, List<String>> d : defMap.entrySet()) {
            if (d.getValue().isEmpty()) continue;
            else {
                HashMap<String, List<String>> defExamples = new HashMap<>();

                List<String> str = d.getValue();
                for (String s : str) {
                    List<String> splt = List.of(s.split(";"));
                    List<String> examples = splt.subList(1, splt.size());

                    defExamples.put(splt.get(0), examples);
                }

                results.put(d.getKey(), defExamples);
            }
        }

        model.addAttribute("definitions", results);
        model.addAttribute("word", word);

        if (requestSource == null) {
            if (principal != null) {
                model.addAttribute("userData", accountService.getAccountInfoByUsername(principal.getName()));
            }
            return "main.html";
        }
        else
            return "fragments/meaning.html";
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
