package com.pbl4.monolingo.controller;

import com.pbl4.monolingo.entity.DailyMission;
import com.pbl4.monolingo.service.AccountService;
import com.pbl4.monolingo.service.DailyMissionService;
import com.pbl4.monolingo.service.DataPerDayService;
import com.pbl4.monolingo.utility.Utility;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@AllArgsConstructor
public class FriendController {

    private final AccountService accountService;
    private final DailyMissionService dailyMissionService;
    private final DataPerDayService dataPerDayService;

    @GetMapping("/searchFriend")
    public String showSearch(Model model, Principal principal,
                            @RequestHeader(value = "request-source", required = false) String requestSource) {

        if (principal != null) {
            int accountId = accountService.getAccountByUsername(principal.getName()).getAccountId();
            List<DailyMission> dailyMissions = dailyMissionService.getMissionByAccountId(dataPerDayService.getDayId(), accountId);
            model.addAttribute("dailyMissions", dailyMissions);

            if (requestSource == null) {
                model.addAttribute("userData", accountService.getAccountInfoByUsername(principal.getName()));
                return "main";
            } else {
                return "fragments/searchFriend";
            }
        }

        return "main";

    }

    @GetMapping("/searchFriend/{name}")
    public String searchFriend(Model model, Principal principal,
                            @PathVariable String name,
                            @RequestHeader(value = "request-source", required = false) String requestSource) {

        if (principal != null) {
            int accountId = accountService.getAccountByUsername(principal.getName()).getAccountId();
            List<DailyMission> dailyMissions = dailyMissionService.getMissionByAccountId(dataPerDayService.getDayId(), accountId);
            model.addAttribute("dailyMissions", dailyMissions);

            model.addAttribute("searchResults", accountService.searchAccount(name));

            if (requestSource == null) {
                model.addAttribute("userData", accountService.getAccountInfoByUsername(principal.getName()));
                return "main";
            } else {
                return "fragments/searchFriend";
            }
        }

        return "main";

    }

}
