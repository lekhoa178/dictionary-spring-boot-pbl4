package com.pbl4.monolingo.controller;

import com.pbl4.monolingo.entity.*;
import com.pbl4.monolingo.service.*;
import com.pbl4.monolingo.utility.dto.AccountExp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.*;
import java.util.stream.IntStream;

@Controller
public class ApplicationController {

    private LearnService learnService;
    private final AccountService accountService;
    private final NotebookService notebookService;
    private final DataPerDayService dataPerDayService;

    private final ExtraInfoService extraInfoService;

    @Autowired
    public ApplicationController(LearnService learnService,
                                 AccountService accountService,
                                 NotebookService notebookService,
                                 DataPerDayService dataPerDayService, ExtraInfoService extraInfoService) {
        this.learnService = learnService;
        this.accountService = accountService;
        this.notebookService = notebookService;
        this.dataPerDayService = dataPerDayService;
        this.extraInfoService = extraInfoService;
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

        if (principal != null) {
            int accountId = accountService.getAccountByUsername(principal.getName()).getAccountId();

            model.addAttribute("stageColors", StageColors);
            model.addAttribute("stages", learnService.getAccountStages(accountId));
        }

        if (requestSource == null && principal != null) {
            model.addAttribute("userData", accountService.getAccountInfoByUsername(principal.getName()));
            return "main";
        } else
            return "fragments/learn";
    }

//    @GetMapping("/practice")
//
//    public String showPractice(Model model, Principal principal,
//                               @RequestHeader(value = "request-source", required = false) String requestSource) {
//        if (principal != null) {
//            int accountId = accountService.getAccountByUsername(principal.getName()).getAccountId();
//            List<Notebook> notebooks = notebookService.getAllNotebooksByAccountId(accountId);
//
//            for (Notebook notebook:
//                    notebooks) {
//                notebook.getLexicon().setWord(notebook.getLexicon().getWord().replace('_', ' '));
//
//                Synset synset = notebook.getLexicon().getSynset();
//                if (synset.getDefinition().length() > 25)
//                    notebook.getLexicon().getSynset().setDefinition(synset.getDefinition().substring(0, 25).concat("..."));
//            }
//            model.addAttribute("notebooks", notebooks);
//
//            if (requestSource == null) {
//                model.addAttribute("userData", accountService.getAccountInfoByUsername(principal.getName()));
//                return "main";
//            }
//        }
//
//        return "fragments/practice";
//    }

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
        } else
            return "fragments/rank";
    }

    @GetMapping("/practice")

    public String showPractice(Model model, Principal principal,
                               @RequestHeader(value = "request-source", required = false) String requestSource) {
        if (principal != null) {
            int accountId = accountService.getAccountByUsername(principal.getName()).getAccountId();
            List<Notebook> notebooks = notebookService.getAllNotebooksByAccountId(accountId);

            for (Notebook notebook :
                    notebooks) {
                notebook.getLexicon().setWord(notebook.getLexicon().getWord().replace('_', ' '));

                Synset synset = notebook.getLexicon().getSynset();
                if (synset.getDefinition().length() > 25)
                    notebook.getLexicon().getSynset().setDefinition(synset.getDefinition().substring(0, 25).concat("..."));
            }
            model.addAttribute("notebooks", notebooks);
        }

        if (requestSource == null & principal != null) {
            model.addAttribute("userData", accountService.getAccountInfoByUsername(principal.getName()));
            return "main";
        } else
            return "fragments/practice";
    }

    @GetMapping("/mission")

    public String showMission(Model model, Principal principal,
                              @RequestHeader(value = "request-source", required = false) String requestSource) {
        if (requestSource == null) {
            if (principal != null) {
                model.addAttribute("userData", accountService.getAccountInfoByUsername(principal.getName()));
            }
            return "main";
        } else
            return "fragments/mission";
    }

    @GetMapping("/store")

    public String showStore(Model model, Principal principal,
                            @RequestHeader(value = "request-source", required = false) String requestSource) {
        model.addAttribute("userData", accountService.getAccountInfoByUsername(principal.getName()));
        if (requestSource == null) {
            if (principal != null) {
                model.addAttribute("userData", accountService.getAccountInfoByUsername(principal.getName()));
            }
            return "main";
        } else
            return "fragments/store";
    }

    @PostMapping("/store/heart")
    @ResponseBody
    public ExtraInformation buyHearts(Principal principal) {
        int heart = 0;
        ExtraInformation temp = new ExtraInformation();
        if (principal != null) {
            Account account = accountService.getAccountByUsername(principal.getName());
            extraInfoService.buyHearts(account.getAccountId());

            temp.setHearts(account.getExtraInformation().getHearts());
            temp.setBalance(account.getExtraInformation().getBalance());
        }
        return temp;
    }

    @GetMapping("/profile")

    public String showProfile(Model model, Principal principal,
                              @RequestHeader(value = "request-source", required = false) String requestSource) {
        if (principal != null) {
            Account account = accountService.getAccountByUsername(principal.getName());
            model.addAttribute("stats", dataPerDayService.getAccountStats(account.getAccountId()));
            model.addAttribute("statPerDay", dataPerDayService.getAccountDPDs(account.getAccountId()));

            if (requestSource == null) {
                model.addAttribute("userData", accountService.getAccountInfoByUsername(principal.getName()));
                return "main";
            }
        }

        return "fragments/profile";
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
}
