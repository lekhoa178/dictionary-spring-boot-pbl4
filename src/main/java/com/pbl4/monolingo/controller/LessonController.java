package com.pbl4.monolingo.controller;

import com.pbl4.monolingo.entity.Account;
import com.pbl4.monolingo.entity.ExtraInformation;
import com.pbl4.monolingo.service.AccountService;
import com.pbl4.monolingo.service.ExtraInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/lesson")
public class LessonController {

    private final AccountService accountService;
    private final ExtraInfoService extraInfoService;
    @Autowired
    public LessonController(ExtraInfoService extraInfoService, AccountService accountService) {
        this.accountService = accountService;
        this.extraInfoService = extraInfoService;
    }

    @GetMapping("/{stageId}/{levelId}")

    public String showLesson(Model model,
                             @PathVariable int stageId,
                             @PathVariable int levelId) {
        model.addAttribute("stage", stageId);
        model.addAttribute("level", levelId);

        return "lesson.html";
    }

    @GetMapping("/finish/{data}")

    public String showLessonFinish(Model model, @PathVariable String data,
                                   @RequestHeader(value = "request-source", required = false) String requestSource) {
        if (requestSource == null)
            return "redirect:/learn";

        int exp = Integer.parseInt(data.split(" ")[0]);
        int precise = Integer.parseInt(data.split(" ")[1]);

        model.addAttribute("exp", exp);
        model.addAttribute("precise", precise);
        return "lessonFinish.html";
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
}
