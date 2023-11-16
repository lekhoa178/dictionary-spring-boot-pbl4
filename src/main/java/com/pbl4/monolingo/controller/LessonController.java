package com.pbl4.monolingo.controller;

import com.pbl4.monolingo.entity.embeddable.LevelId;
import com.pbl4.monolingo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/lesson")
public class LessonController {



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

}
