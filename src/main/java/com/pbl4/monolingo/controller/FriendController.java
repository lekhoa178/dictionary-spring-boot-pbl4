package com.pbl4.monolingo.controller;

import com.pbl4.monolingo.entity.Account;
import com.pbl4.monolingo.entity.DailyMission;
import com.pbl4.monolingo.service.AccountService;
import com.pbl4.monolingo.service.DailyMissionService;
import com.pbl4.monolingo.service.DataPerDayService;
import com.pbl4.monolingo.service.FriendService;
import com.pbl4.monolingo.utility.Utility;
import com.pbl4.monolingo.utility.dto.AccountStats;
import gov.nih.nlm.nls.lvg.Util.Str;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@AllArgsConstructor
public class FriendController {

    private final AccountService accountService;
    private final DailyMissionService dailyMissionService;
    private final DataPerDayService dataPerDayService;
    private final FriendService friendService;

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

            model.addAttribute("searchResults", accountService.searchFriend(name, accountId));

            if (requestSource == null) {
                model.addAttribute("userData", accountService.getAccountInfoByUsername(principal.getName()));
                return "main";
            } else {
                return "fragments/searchFriend";
            }
        }

        return "main";

    }

    @PostMapping("/follow/{name}")
    public String follow(Model model, Principal principal,
                               @RequestBody int followingId,
                               @PathVariable String name,
                               @RequestHeader(value = "request-source", required = false) String requestSource) {

        if (principal != null) {
            int currentId = accountService.getAccountByUsername(principal.getName()).getAccountId();
            friendService.follow(currentId, followingId);

            model.addAttribute("searchResults", accountService.searchFriend(name, currentId));

            return "fragments/searchFriend";
        }

        return "main";

    }

    @PostMapping("/unfollow/{name}")
    public String unfollow(Model model, Principal principal,
                         @RequestBody int followingId,
                         @PathVariable String name,
                         @RequestHeader(value = "request-source", required = false) String requestSource) {

        if (principal != null) {
            int currentId = accountService.getAccountByUsername(principal.getName()).getAccountId();
            friendService.unFollow(currentId, followingId);

            model.addAttribute("searchResults", accountService.searchFriend(name, currentId));

            return "fragments/searchFriend";
        }

        return "main";

    }

    @GetMapping("/profile/{accountId}")

    public String showProfile(Model model, Principal principal,
                              @PathVariable int accountId,
                              @RequestHeader(value = "request-source", required = false) String requestSource) {
        if (principal != null) {
            Account account = accountService.getAccountByUsername(principal.getName());
            model.addAttribute("stats", dataPerDayService.getAccountStats(accountId));
            model.addAttribute("dayStats", dataPerDayService.getAccountDPDStat(accountId));
            model.addAttribute("friendsExps", friendService.getFollowingExps(accountId));
            model.addAttribute("current", false);
            List<DailyMission> dailyMissions = dailyMissionService.getMissionByAccountId(dataPerDayService.getDayId(), account.getAccountId());
            model.addAttribute("dailyMissions", dailyMissions);

            if (requestSource == null) {
                model.addAttribute("userData", accountService.getAccountInfoByUsername(principal.getName()));
                return "main";
            }
        }

        return "fragments/profile";
    }

}
