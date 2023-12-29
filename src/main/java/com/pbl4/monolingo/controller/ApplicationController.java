package com.pbl4.monolingo.controller;

import com.pbl4.monolingo.entity.*;
import com.pbl4.monolingo.service.*;
import com.pbl4.monolingo.utility.Utility;
import com.pbl4.monolingo.utility.dto.AccountDPDStat;
import com.pbl4.monolingo.utility.dto.AccountExp;
import com.pbl4.monolingo.utility.dto.AccountStats;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.SimpleFormatter;
import java.util.stream.IntStream;

@Controller
@AllArgsConstructor
public class ApplicationController {

    private LearnService learnService;
    private final AccountService accountService;
    private final NotebookService notebookService;
    private final DataPerDayService dataPerDayService;
    private final DailyMissionService dailyMissionService;
    private final ExtraInfoService extraInfoService;
    private final FriendService friendService;

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
            List<DailyMission> dailyMissions = dailyMissionService.getMissionByAccountId(dataPerDayService.getDayId(), accountId);
            model.addAttribute("dailyMissions", dailyMissions);

            model.addAttribute("stageColors", Utility.StageColors);
            model.addAttribute("stages", learnService.getAccountStages(accountId));
            model.addAttribute("accountId", accountId);

            if (requestSource == null) {
                model.addAttribute("userData", accountService.getAccountInfoByUsername(principal.getName()));
                return "main";
            } else {
                return "fragments/learn";
            }
        }

        return "main";

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
            List<DailyMission> dailyMissions = dailyMissionService.getMissionByAccountId(dataPerDayService.getDayId(), curAccountId);
            model.addAttribute("dailyMissions", dailyMissions);

            OptionalInt index = IntStream.range(0, accountExps.size())
                    .filter(i -> curAccountId == accountExps.get(i).getAccount().getAccountId())
                    .findFirst();

            model.addAttribute("records", accountExps);
            model.addAttribute("current", currentAcc);

            model.addAttribute("foundIndex", index.isPresent() ? index.getAsInt() : -1);

            if (requestSource == null) {
                model.addAttribute("accountId", curAccountId);
                model.addAttribute("userData", accountService.getAccountInfoByUsername(principal.getName()));
                return "main";
            } else {
                return "fragments/rank";
            }
        }

        return "main";
    }

    @GetMapping("/practice")

    public String showPractice(Model model, Principal principal,
                               @RequestHeader(value = "request-source", required = false) String requestSource) {
        if (principal != null) {
            int accountId = accountService.getAccountByUsername(principal.getName()).getAccountId();
            List<Notebook> notebooks = notebookService.getAllNotebooksByAccountId(accountId);
            List<DailyMission> dailyMissions = dailyMissionService.getMissionByAccountId(dataPerDayService.getDayId(), accountId);
            model.addAttribute("dailyMissions", dailyMissions);

            for (Notebook notebook :
                    notebooks) {
                notebook.getLexicon().setWord(notebook.getLexicon().getWord().replace('_', ' '));

                Synset synset = notebook.getLexicon().getSynset();
                if (synset.getDefinition().length() > 25)
                    notebook.getLexicon().getSynset().setDefinition(synset.getDefinition().substring(0, 25).concat("..."));
            }
            model.addAttribute("notebooks", notebooks);

            if (requestSource == null) {
                model.addAttribute("accountId", accountId);
                model.addAttribute("userData", accountService.getAccountInfoByUsername(principal.getName()));
                return "main";
            } else {
                return "fragments/practice";
            }
        }


        return "main";
    }

    @GetMapping("/mission")

    public String showMission(Model model, Principal principal,
                              @RequestHeader(value = "request-source", required = false) String requestSource) {
        if (principal != null) {
            int accountId = accountService.getAccountByUsername(principal.getName()).getAccountId();
            List<DailyMission> dailyMissions = dailyMissionService.getMissionByAccountId(dataPerDayService.getDayId(), accountId);
            model.addAttribute("dailyMissions", dailyMissions);

            if (requestSource == null) {
                model.addAttribute("accountId", accountId);
                model.addAttribute("userData", accountService.getAccountInfoByUsername(principal.getName()));
                return "main";
            } else {
                return "fragments/mission";
            }
        }

        return "main";

    }

    @GetMapping("/store")

    public String showStore(Model model, Principal principal,
                            @RequestHeader(value = "request-source", required = false) String requestSource) {
        if (principal != null) {
            int accountId = accountService.getAccountByUsername(principal.getName()).getAccountId();
            List<DailyMission> dailyMissions = dailyMissionService.getMissionByAccountId(dataPerDayService.getDayId(), accountId);
            model.addAttribute("dailyMissions", dailyMissions);

            model.addAttribute("userData", accountService.getAccountInfoByUsername(principal.getName()));

            if (requestSource == null) {
                model.addAttribute("accountId", accountId);
                return "main";
            } else {
                return "fragments/store";
            }
        }

        return "main";

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
            AccountStats dataPerDay = dataPerDayService.getAccountStats(account.getAccountId());
            model.addAttribute("stats", dataPerDayService.getAccountStats(account.getAccountId()));
            model.addAttribute("dayStats", dataPerDayService.getAccountDPDStat(account.getAccountId()));
            model.addAttribute("friendsExps", friendService.getFollowingExps(account.getAccountId()));
            model.addAttribute("current", true);

            List<DailyMission> dailyMissions = dailyMissionService.getMissionByAccountId(dataPerDayService.getDayId(), account.getAccountId());
            model.addAttribute("dailyMissions", dailyMissions);

            if (requestSource == null) {
                model.addAttribute("userData", accountService.getAccountInfoByUsername(principal.getName()));
                return "main";
            }
        }

        return "fragments/profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(Model model, Principal principal, @RequestParam("profile-name") String name,
                                @RequestParam("gender-rd") String gender, @RequestParam("profile-birthDate") String birthDate,
                                @RequestParam("profile-email") String email) throws ParseException {
        if (principal != null) {
            Account account = accountService.getAccountByUsername(principal.getName());

            account.setName(name);
            account.setGender(Boolean.parseBoolean(gender));

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            account.setBirthdate(formatter.parse(birthDate));
            account.setEmail(email);

            accountService.saveAccount(account);
        }
        return showProfile(model, principal, "request-source");
    }

    @GetMapping("/test-profile")

    public String showTest(Model model, Principal principal,
                              @RequestHeader(value = "request-source", required = false) String requestSource) {
        Map<String, Integer> surveyMap = new LinkedHashMap<>();
        surveyMap.put("Java", 40);
        surveyMap.put("Python", 50);
        surveyMap.put(".NET", 20);
        model.addAttribute("surveyMap", surveyMap);
        return "test";
    }

    @GetMapping("/lesson")

    public String showLesson(Model model) {
        return "lesson";
    }
}
