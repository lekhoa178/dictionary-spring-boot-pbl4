package com.pbl4.monolingo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/lesson")
public class LessonController {

    @GetMapping("/")

    public String showLesson(Model model) {
        return "lesson.html";
    }

    @GetMapping("/finish/{data}")

    public String showLessonFinish(Model model, @PathVariable String data,
                                   @RequestHeader(value = "request-source", required = false) String requestSource) {
        if (requestSource == null)
            return "redirect:/learn";

        model.addAttribute("exp", data.split(" ")[0]);
        model.addAttribute("precise", data.split(" ")[1]);
        return "lessonFinish.html";
    }

}
