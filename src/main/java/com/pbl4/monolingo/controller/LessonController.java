package com.pbl4.monolingo.controller;

import com.pbl4.monolingo.dao.LevelFulfillRepository;
import com.pbl4.monolingo.entity.embeddable.LevelId;
import com.pbl4.monolingo.service.AccountService;
import com.pbl4.monolingo.service.LearnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/lesson")
public class LessonController {

    private final LearnService learnService;
    private final AccountService accountService;

    @Autowired
    public LessonController(LearnService learnService, AccountService accountService) {
        this.learnService = learnService;
        this.accountService = accountService;
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

        int exp = Integer.parseInt(data.split(" ")[0]);
        int precise = Integer.parseInt(data.split(" ")[1]);

        model.addAttribute("exp", exp);
        model.addAttribute("precise", precise);

        int accountId = accountService.getAccountByUsername(principal.getName()).getAccountId();
        learnService.finishLevel(accountId, stageId, levelId);
        return "lessonFinish";
    }

}
