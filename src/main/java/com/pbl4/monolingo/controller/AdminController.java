package com.pbl4.monolingo.controller;

import com.pbl4.monolingo.entity.Account;
import com.pbl4.monolingo.entity.Level;
import com.pbl4.monolingo.entity.Stage;
import com.pbl4.monolingo.entity.Vocabulary;
import com.pbl4.monolingo.entity.embeddable.LevelId;
import com.pbl4.monolingo.service.AccountService;
import com.pbl4.monolingo.service.DictionaryService;
import com.pbl4.monolingo.service.LevelService;
import com.pbl4.monolingo.service.StageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private DictionaryService dictionaryService;
    private AccountService accountService;
    private StageService stageService;
    private LevelService levelService;



    @Autowired
    public AdminController(DictionaryService dictionaryController,
                           AccountService accountService, StageService stageService, LevelService levelService) {
        this.dictionaryService = dictionaryController;
        this.accountService = accountService;
        this.stageService = stageService;
        this.levelService = levelService;
    }


    @GetMapping("/account")

    public String showAdminAccount(Model model, Principal principal,
                            @RequestHeader(value = "request-source", required = false) String requestSource) {
        return showAccountPerPage(model, principal,requestSource, 0);
    }

    @GetMapping("/stage")

    public String showAdminStage(Model model, Principal principal,
                                     @RequestHeader(value = "request-source", required = false) String requestSource) {

        List<Stage> stages = stageService.getAllStage();

        model.addAttribute("stages", stages);

        if (requestSource == null) {
            if (principal != null) {
                model.addAttribute("userData", accountService.getAccountInfoByUsername(principal.getName()));
            }
            return "admin";
        }
        else
            return "fragments_admin/stage";
    }

    @GetMapping("/level/{stageId}")

    public String showAdminLevel(Model model, Principal principal,
                                 @RequestHeader(value = "request-source", required = false) String requestSource,
                                 @PathVariable(value = "stageId") int stageId) {

        List<Level> levels = levelService.getLevelByStageId(stageId);

        model.addAttribute("levels", levels);
        model.addAttribute("stage", stageId);
        return "fragments_admin/level";
    }

    @GetMapping("/vocabulary")

    public String showAdminVocabulary(Model model, Principal principal,
                                 @RequestHeader(value = "request-source", required = false) String requestSource,
                                      @RequestParam int levelId, @RequestParam int stageId) {

        List<Vocabulary> vocabularies = levelService.getVocabularyByLevelId(new LevelId(stageId, levelId));

        model.addAttribute("vocabularies", vocabularies);

        return "fragments_admin/vocabulary";
    }

    @GetMapping("/account/page/{pageNo}")

    public String showAccountPerPage(Model model, Principal principal,
                                     @RequestHeader(value = "request-source", required = false) String requestSource,
                                     @PathVariable(value = "pageNo") int pageNo) {

        Page<Account> pages = accountService.getAccountWithPage(pageNo, 10);
        List<Account> accounts = pages.getContent();

        model.addAttribute("accounts", accounts);
        model.addAttribute("totalPage", pages.getTotalPages());
        model.addAttribute("currentPage", pageNo);

        if (requestSource == null) {
            if (principal != null) {
                model.addAttribute("userData", accountService.getAccountInfoByUsername(principal.getName()));
            }
            return "admin";
        }
        else
            return "fragments_admin/account";
    }
    @GetMapping("/account/search/{keyword}")

    public String showSearchList(Model model, Principal principal,
                                 @RequestHeader(value = "request-source", required = false) String requestSource,
                                 @PathVariable (value = "keyword") String keyword) {

        List<Account> accounts = accountService.searchAccount(keyword);

        int pageNum = accounts.size() / 10;

        Pageable pageable = PageRequest.of(pageNum, 10);

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), accounts.size());

        Page<Account> page = new PageImpl<>(accounts.subList(start, end), pageable, accounts.size());

        model.addAttribute("accounts", accounts);
        model.addAttribute("totalPage", page.getTotalPages());
        model.addAttribute("currentPage", 1);

        if (requestSource == null) {
            if (principal != null) {
                model.addAttribute("userData", accountService.getAccountInfoByUsername(principal.getName()));
            }
            return "admin";
        }
        else
            return "fragments_admin/account";
    }
    @GetMapping("/account/add")

    public String showAddForm(Model model, Principal principal,
                              @RequestHeader(value = "request-source", required = false) String requestSource) {
        Account account = new Account();
        account.setAccountId(0);
        model.addAttribute("account", account);

        return "fragments_admin/account-form";
    }
    @PostMapping("/account/update/{accountId}")

    public String showUpateForm(Model model, Principal principal,
                                @RequestHeader(value = "request-source", required = false) String requestSource,
                                @PathVariable(value = "accountId") int accountId) {
        Account account = accountService.getAccountById(accountId);
        model.addAttribute("account", account);

        return "fragments_admin/account-form";
    }
    @PostMapping("/account/delete")

    public String delete(@RequestParam("accountId") int accountId, Model model) {
        accountService.deleteAccountById(accountId);
        Page<Account> pages = accountService.getAccountWithPage(0, 10);
        List<Account> accounts = pages.getContent();

        model.addAttribute("accounts", accounts);
        model.addAttribute("totalPage", pages.getTotalPages());
        model.addAttribute("currentPage", 0);

        return "fragments_admin/account";
    }
    @PostMapping("/account/deleteMany")

    public String deleteMany(Model model,@RequestParam("selected-rows") List<Integer> accountIds) {

        if (!accountIds.isEmpty()) {
            for (int accountId : accountIds) {
                accountService.deleteAccountById(accountId);
            }
        }
        Page<Account> pages = accountService.getAccountWithPage(0, 10);
        List<Account> accounts = pages.getContent();

        model.addAttribute("accounts", accounts);
        model.addAttribute("totalPage", pages.getTotalPages());
        model.addAttribute("currentPage", 0);

        return "fragments_admin/account";
    }
    @PostMapping("/account/save")
    public String saveAccount(@ModelAttribute("account") Account  account) {
        accountService.saveAccount(account);
        return "redirect:/admin/account";
    }

    @GetMapping("/stage/add")

    public String showStageAddForm(Model model, Principal principal,
                              @RequestHeader(value = "request-source", required = false) String requestSource) {

        Stage stage = new Stage();
        stage.setStageId(0);

        model.addAttribute("stage", stage);

        return "fragments_admin/stage-form";
    }

    @GetMapping("/stage/update/{stageId}")

    public String showStageUpdateForm(Model model, Principal principal,
                                @RequestHeader(value = "request-source", required = false) String requestSource,
                                @PathVariable(value = "stageId") int stageId) {

        Stage stage = stageService.getStageById(stageId);
        System.out.println(stage.getStageId());
        model.addAttribute("stage", stage);

        return "fragments_admin/stage-form";
    }

    @PostMapping("/stage/save")
    public String saveStage(@ModelAttribute("stage") Stage stage) {

        stageService.save(stage);
        return "redirect:/admin/stage";
    }

    @PostMapping("/level/add")
    public void addLevel(@RequestParam int stageId, @RequestParam int levelId) {
        LevelId lvId = new LevelId(stageId, levelId);
    }
}
