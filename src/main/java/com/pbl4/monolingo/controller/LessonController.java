package com.pbl4.monolingo.controller;

import com.pbl4.monolingo.dao.LevelFulfillRepository;
import com.pbl4.monolingo.entity.DataPerDay;
import com.pbl4.monolingo.entity.LevelFulfill;
import com.pbl4.monolingo.entity.embeddable.LevelId;
import com.pbl4.monolingo.service.*;
import com.pbl4.monolingo.entity.Account;
import com.pbl4.monolingo.entity.ExtraInformation;
import com.pbl4.monolingo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/lesson")
public class LessonController {

    private final LearnService learnService;
    private final AccountService accountService;
    private final ExtraInfoService extraInfoService;
    private final DataPerDayService dataPerDayService;
    private final LevelFulfillRepository levelFulfillRepository;
    private final DailyMissionService dailyMissionService;
    
    @Autowired
    public LessonController(LearnService learnService,
                            ExtraInfoService extraInfoService,
                            AccountService accountService,
                            DataPerDayService dataPerDayService,
                            LevelFulfillRepository levelFulfillRepository,
                            DailyMissionService dailyMissionService) {
        this.learnService = learnService;
        this.accountService = accountService;
        this.extraInfoService = extraInfoService;
        this.dataPerDayService = dataPerDayService;
        this.levelFulfillRepository = levelFulfillRepository;
        this.dailyMissionService = dailyMissionService;
    }

    @GetMapping("/{stageId}/{levelId}")

    public String showLesson(Model model, Principal principal,
                             @PathVariable int stageId,
                             @PathVariable int levelId) {
        Account account = accountService.getAccountByUsername(principal.getName());

        model.addAttribute("type", "learn");

        model.addAttribute("accountId", account.getAccountId());
        model.addAttribute("stage", stageId);
        model.addAttribute("level", levelId);
        model.addAttribute("account", account);

        return "lesson";
    }

    @GetMapping("/practice")

    public String showPracticeNotebook(Principal principal,
                                        Model model) {
        Account account = accountService.getAccountByUsername(principal.getName());


        model.addAttribute("type", "practice");

        model.addAttribute("accountId", account.getAccountId());
        model.addAttribute("stage", -1);
        model.addAttribute("level", -1);
        model.addAttribute("account", account);

        return "lesson.html";
    }

    @GetMapping("/listen")
    public String showPracticeListen(Principal principal,
                                       Model model) {
        Account account = accountService.getAccountByUsername(principal.getName());
        List<LevelFulfill> fulfills = levelFulfillRepository.findAllByIdAccountId(account.getAccountId());
        int rand = (new Random()).nextInt(0, fulfills.size());

        model.addAttribute("type", "listen");

        model.addAttribute("accountId", account.getAccountId());
        model.addAttribute("stage", fulfills.get(rand).getId().getLevelId().getStageId());
        model.addAttribute("level", fulfills.get(rand).getId().getLevelId().getLevelId());
        model.addAttribute("account", account);

        return "lesson.html";
    }

    @GetMapping("/finish/{stageId}/{levelId}/{fulfilled}/{data}/{type}")

    public String showLessonFinish(Model model, Principal principal,
                                   @PathVariable String data,
                                   @PathVariable int stageId,
                                   @PathVariable int levelId,
                                   @PathVariable boolean fulfilled,
                                   @PathVariable String type,
                                   @RequestHeader(value = "request-source", required = false) String requestSource) {
        if (requestSource == null)
            return "redirect:/learn";


        int accountId = accountService.getAccountByUsername(principal.getName()).getAccountId();

        int exp = Integer.parseInt(data.split(" ")[0]);
        float precise = Float.parseFloat(data.split(" ")[1]);

        model.addAttribute("exp", exp);
        model.addAttribute("precise", precise);
        model.addAttribute("streak", extraInfoService.updateExtraInfo(accountService.getAccountById(accountId)));
        model.addAttribute("streakDays", accountService.getAccountInfoByUsername(principal.getName()).getNumberOfConsecutiveDay());

        if (!fulfilled && stageId != -1 && precise > 60)
            learnService.finishLevel(accountId, stageId, levelId);

        dataPerDayService.updateAccountDPD(accountId, exp, 0);

        int earned = dailyMissionService.updateDailyMission(accountId, exp, precise, type);
        extraInfoService.updateCoin(accountId, earned);

        model.addAttribute("dailyMissions",
                dailyMissionService.getMissionByAccountId(dataPerDayService.getDayId(), accountId));

        return "lessonFinish";
    }

    @PostMapping("/lostHeart")
    public ResponseEntity<Integer> lostHeart(Principal principal) {
        Account account = accountService.getAccountByUsername(principal.getName());
        extraInfoService.loseHeart(account.getAccountId());
        System.out.println(account.getExtraInformation().getHearts());
        return ResponseEntity.ok(account.getExtraInformation().getHearts());
    }

    @GetMapping("/heart")
    public ResponseEntity<Integer> getHeart(Principal principal) {
        return ResponseEntity.ok(accountService.getAccountInfoByUsername(principal.getName()).getHearts());
    }

    @GetMapping("/test/{data}")
    public String test(Model model, Principal principal,
                                        @PathVariable String data) {
        int accountId = accountService.getAccountByUsername(principal.getName()).getAccountId();

        int exp = Integer.parseInt(data.split(" ")[0]);
        int precise = Integer.parseInt(data.split(" ")[1]);

        model.addAttribute("exp", exp);
        model.addAttribute("precise", precise);

        dataPerDayService.updateAccountDPD(accountId, exp, 0);
        return "lessonFinish";
    }
}
