package com.pbl4.monolingo.controller;

import com.pbl4.monolingo.entity.*;
import com.pbl4.monolingo.entity.embeddable.LevelId;
import com.pbl4.monolingo.entity.embeddable.VocabularyId;
import com.pbl4.monolingo.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private DictionaryService dictionaryService;
    private AccountService accountService;
    private StageService stageService;
    private LevelService levelService;
    private VocabularyService vocabularyService;
    private MissionService missionService;


    @Autowired
    public AdminController(DictionaryService dictionaryController,
                           AccountService accountService, StageService stageService, LevelService levelService,
                           VocabularyService vocabularyService, MissionService missionService) {
        this.dictionaryService = dictionaryController;
        this.accountService = accountService;
        this.stageService = stageService;
        this.levelService = levelService;
        this.vocabularyService = vocabularyService;
        this.missionService = missionService;
    }


    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class ,stringTrimmerEditor);
    }

    @GetMapping("/account")

    public String showAdminAccount(Model model, Principal principal,
                                   @RequestHeader(value = "request-source", required = false) String requestSource) {
        return showAccountPerPage(model, principal, requestSource, 0);
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
        } else
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

    public String showAdminVocabulary(Model model, @RequestParam int levelId, @RequestParam int stageId) {

        List<Vocabulary> vocabularies = levelService.getVocabularyByLevelId(new LevelId(stageId, levelId));

        model.addAttribute("stageId", stageId);
        model.addAttribute("levelId", levelId);
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
        } else
            return "fragments_admin/account";
    }

    @GetMapping("/account/search/{keyword}")

    public String showSearchList(Model model, Principal principal,
                                 @RequestHeader(value = "request-source", required = false) String requestSource,
                                 @PathVariable(value = "keyword") String keyword) {

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
        } else
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

    public String deleteMany(Model model, @RequestParam("selected-rows") List<Integer> accountIds) {

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
    public String saveAccount(@ModelAttribute("account") Account account) {
        if (account.getAccountId() != 0)
            account.setPassword(accountService.getAccountById(account.getAccountId()).getPassword());

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
        model.addAttribute("stage", stage);

        return "fragments_admin/stage-form";
    }

    @PostMapping("/stage/save")
    public String saveStage(@ModelAttribute("stage") Stage stage) {
        List<Level> levels = levelService.getLevelByStageId(stage.getStageId());
        stage.setLevels(levels);

        stageService.save(stage);
        return "redirect:/admin/stage";
    }

    @PostMapping("/level/add")
    public String addLevel(@RequestParam int stageId, @RequestParam int levelId) {
        LevelId lvId = new LevelId(stageId, levelId + 1);
        Level newLevel = new Level(lvId);
        levelService.save(newLevel);

        return "redirect:/admin/level/" + stageId;
    }


    @GetMapping("/vocabulary/add")
    public String addVocabulary(Model model, @RequestParam int stageId, @RequestParam int levelId) {
        model.addAttribute("stageId", stageId);
        model.addAttribute("levelId", levelId);

        return "fragments_admin/vocabulary-form";
    }

    @PostMapping("/vocabulary/save")
    public String saveVocabulary(Model model, @RequestBody List<Vocabulary> vocabularies) {
        int stageId = vocabularies.get(0).getId().getLevelId().getStageId();
        int levelId = vocabularies.get(0).getId().getLevelId().getLevelId();

        for (Vocabulary vocabulary : vocabularies) {
            if (vocabulary.getId().getVocabularyNum() == 0)
                vocabulary.setId(new VocabularyId(new LevelId(stageId, levelId), vocabularyService.findMaxId() + 1));

            Vocabulary rs = vocabularyService.save(vocabulary);
        }
        List<Vocabulary> temp_vocabularies = levelService.getVocabularyByLevelId(new LevelId(stageId, levelId));

        model.addAttribute("stageId", stageId);
        model.addAttribute("levelId", levelId);
        model.addAttribute("vocabularies", temp_vocabularies);

        return "fragments_admin/vocabulary";
    }

    @PostMapping("/vocabulary/delete")
    public String deleteVocabulary(Model model, @RequestBody VocabularyId id) {
        int stageId = id.getLevelId().getStageId();
        int levelId = id.getLevelId().getLevelId();

        vocabularyService.deleteVocabularyById(new VocabularyId(new LevelId(stageId, levelId), id.getVocabularyNum()));

        List<Vocabulary> temp_vocabularies = levelService.getVocabularyByLevelId(new LevelId(stageId, levelId));

        model.addAttribute("stageId", stageId);
        model.addAttribute("levelId", levelId);
        model.addAttribute("vocabularies", temp_vocabularies);

        return "fragments_admin/vocabulary";
    }

    @GetMapping("/mission")
    public String showAdminMission(Model model, Principal principal,
                                   @RequestHeader(value = "request-source", required = false) String requestSource) {
        List<Mission> missions = missionService.getAllMissions();

        model.addAttribute("missions", missions);

        if (requestSource == null) {
            if (principal != null) {
                model.addAttribute("userData", accountService.getAccountInfoByUsername(principal.getName()));
            }
            return "admin";
        } else
            return "fragments_admin/mission";
    }

    @GetMapping("/mission/add")
    public String showMissionForm(Model model, Principal principal,
                                  @RequestHeader(value = "request-source", required = false) String requestSource) {
        Mission mission = new Mission();
        mission.setId(0);

        model.addAttribute("mission", mission);

        return "fragments_admin/mission-form";
    }

    @GetMapping("/mission/update/{missionId}")
    public String showMissionForm(Model model, Principal principal,
                                  @RequestHeader(value = "request-source", required = false) String requestSource,
                                  @PathVariable int missionId) {
        Mission mission = missionService.getMissionById(missionId);

        model.addAttribute("mission", mission);

        return "fragments_admin/mission-form";
    }

    @PostMapping("/mission/save")
    public String saveMission(Model model, Principal principal, @Valid @ModelAttribute("mission") Mission mission, BindingResult bindingResult) {
        System.out.println(mission);
        if (bindingResult.hasErrors()){
            return "fragments_admin/mission-form";
        }
        else {
            missionService.saveMission(mission);
            return showAdminMission(model, principal, "request-source");
        }
    }

}

