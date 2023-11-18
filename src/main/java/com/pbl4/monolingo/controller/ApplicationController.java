package com.pbl4.monolingo.controller;

import com.pbl4.monolingo.dao.*;
import com.pbl4.monolingo.entity.*;
import com.pbl4.monolingo.service.AccountService;
import com.pbl4.monolingo.service.DataPerDayService;
import com.pbl4.monolingo.service.NotebookService;
import com.pbl4.monolingo.utility.dto.AccountExp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.*;
import java.util.stream.IntStream;

@Controller
public class ApplicationController {
    private final StageRepository stageRepository;
    private final AccountService accountService;
    private final NotebookService notebookService;
    private final DataPerDayService dataPerDayService;


    @Autowired
    public ApplicationController(StageRepository stageRepository,
                                 AccountService accountService,
                                 NotebookService notebookService,
                                 DataPerDayService dataPerDayService) {
        this.stageRepository = stageRepository;
        this.accountService = accountService;
        this.notebookService = notebookService;
        this.dataPerDayService = dataPerDayService;
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
        Account account = accountService.getAccountByUsername(principal.getName());
        List<Notebook> notebooks = notebookService.getAllNotebooksByAccountId(account.getAccountId());

        for (Notebook notebook:
             notebooks) {
            notebook.getLexicon().setWord(notebook.getLexicon().getWord().replace('_', ' '));
        }
        model.addAttribute("notebooks", notebooks);

        if (requestSource == null) {
            if (principal != null) {
                model.addAttribute("userData", accountService.getAccountInfoByUsername(principal.getName()));
            }
            return "main";
        }
        else
            return "fragments_admin/test";
    }

    @GetMapping("/rank")

    public String showRank(Model model, Principal principal,
                           @RequestHeader(value = "request-source", required = false) String requestSource) {

        if (principal != null) {
            int curAccountId = accountService.getAccountByUsername(principal.getName()).getAccountId();

            List<AccountExp> accountExps = dataPerDayService.getAllAccountOrderBySumExp();
            AccountExp currentAcc = dataPerDayService.getAccountSumExpById(curAccountId);

            OptionalInt index = IntStream.range(0, accountExps.size())
                    .filter(i -> curAccountId == accountExps.get(i).getAccount().getAccountId())
                    .findFirst();

            model.addAttribute("records", accountExps);
            model.addAttribute("current", currentAcc);

            model.addAttribute("foundIndex", index.isPresent() ? index.getAsInt() : -1);
        }

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

    private List<String> StageColors = new ArrayList<>(Arrays.asList(
            "#58cc02",
            "#ce82ff",
            "#00cd9c",
            "#1cb0f6",
            "#ff86d0",
            "#ff9600",
            "#cc348d"
    ));
    @GetMapping("/test")
    public String showTest(Model model) {
        List<Account> accounts = accountService.getAllAccount();
        model.addAttribute("accounts", accounts);
        return "fragments_admin/test";
    }
}
