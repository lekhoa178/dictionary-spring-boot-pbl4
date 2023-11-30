package com.pbl4.monolingo.controller;

import com.pbl4.monolingo.dao.LevelFulfillRepository;
import com.pbl4.monolingo.entity.DataPerDay;
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

@Controller
@RequestMapping("/lesson")
public class LessonController {

    private final LearnService learnService;
    private final AccountService accountService;
    private final ExtraInfoService extraInfoService;
    private final DataPerDayService dataPerDayService;
    
    @Autowired
    public LessonController(LearnService learnService, ExtraInfoService extraInfoService, AccountService accountService, DataPerDayService dataPerDayService) {
        this.learnService = learnService;
        this.accountService = accountService;
        this.extraInfoService = extraInfoService;
        this.dataPerDayService = dataPerDayService;
    }

    @GetMapping("/{stageId}/{levelId}")

    public String showLesson(Model model,
                             @PathVariable int stageId,
                             @PathVariable int levelId) {
        model.addAttribute("stage", stageId);
        model.addAttribute("level", levelId);

        return "lesson.html";
    }

    @GetMapping("/finish/{stageId}/{levelId}/{data}")

    public String showLessonFinish(Model model, Principal principal,
                                   @PathVariable String data,
                                   @PathVariable int stageId,
                                   @PathVariable int levelId,
                                   @RequestHeader(value = "request-source", required = false) String requestSource) {
        if (requestSource == null)
            return "redirect:/learn";

        System.out.println(data);


        int accountId = accountService.getAccountByUsername(principal.getName()).getAccountId();

        int exp = Integer.parseInt(data.split(" ")[0]);
        float precise = Float.parseFloat(data.split(" ")[1]);

        model.addAttribute("exp", exp);
        model.addAttribute("precise", precise);


        dataPerDayService.updateAccountDPD(accountId, exp, 0);

        if (!learnService.isDoneLevel(accountId, stageId, levelId)) {
            learnService.finishLevel(accountId, stageId, levelId);
            System.out.println("In use");
        }
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
