package com.pbl4.monolingo.controller;

import com.pbl4.monolingo.dao.*;
import com.pbl4.monolingo.entity.*;
import com.pbl4.monolingo.service.AccountService;
import com.pbl4.monolingo.service.DictionaryService;
import com.pbl4.monolingo.service.NotebookService;
import com.pbl4.monolingo.utility.uimodel.DefinitionDetailView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.*;

@Controller
public class ApplicationController {
    private StageRepository stageRepository;
    private AccountService accountService;
    private NotebookService notebookService;
    private DictionaryService dictionaryService;

    @Autowired
    public ApplicationController(DictionaryService dictionaryService,
                                 StageRepository stageRepository,
                                 AccountService accountService, NotebookService notebookService) {
        this.dictionaryService = dictionaryService;
        this.stageRepository = stageRepository;
        this.accountService = accountService;
        this.notebookService = notebookService;
    }

    // add default endpoint

    @GetMapping("/")
    public String showMain(RedirectAttributes redirectAttributes) {

        return "redirect:/learn";
        //return "main.html";
    }

    @GetMapping("/learn")
    public String showLearn(Model model, Principal principal,
                            @RequestHeader(value = "request-source", required = false) String requestSource) {

        model.addAttribute("stageColors", StageColors);
        model.addAttribute("stages", stageRepository.findAll());
        if (requestSource == null) {
            if (principal != null) {
                model.addAttribute("userData", accountService.getAccountInfoByUsername(principal.getName()));
            }
            return "main";
        }
        else
            return "fragments/learn";
    }

    @GetMapping("/practice")

    public String showPractice(Model model, Principal principal,
                               @RequestHeader(value = "request-source", required = false) String requestSource) {
        System.out.println(principal.getName());
        List<Notebook> notebooks = notebookService.findAll();
        for (Notebook notebook:
             notebooks) {
            notebook.getLexicon().setWord(notebook.getLexicon().getWord().replace('_', ' '));
        }
        model.addAttribute("notebooks", notebooks);

        if (requestSource == null) {
            if (principal != null) {
                model.addAttribute("userData", accountService.getAccountInfoByUsername(principal.getName()));
            }
            return "main";
        }
        else
            return "fragments_admin/test";
    }

    @GetMapping("/rank")

    public String showRank(Model model, Principal principal,
                           @RequestHeader(value = "request-source", required = false) String requestSource) {
        if (requestSource == null) {
            if (principal != null) {
                model.addAttribute("userData", accountService.getAccountInfoByUsername(principal.getName()));
            }
            return "main";
        }
        else
            return "fragments/rank";
    }

    @GetMapping("/mission")

    public String showMission(Model model, Principal principal,
                              @RequestHeader(value = "request-source", required = false) String requestSource) {
        if (requestSource == null) {
            if (principal != null) {
                model.addAttribute("userData", accountService.getAccountInfoByUsername(principal.getName()));
            }
            return "main";
        }
        else
            return "fragments/mission";
    }

    @GetMapping("/store")

    public String showStore(Model model, Principal principal,
                            @RequestHeader(value = "request-source", required = false) String requestSource) {
        if (requestSource == null) {
            if (principal != null) {
                model.addAttribute("userData", accountService.getAccountInfoByUsername(principal.getName()));
            }
            return "main";
        }
        else
            return "fragments/store";
    }
    @GetMapping("/admin/account")

    public String showAdmin(Model model, Principal principal,
                            @RequestHeader(value = "request-source", required = false) String requestSource) {
        return showAccountPerPage(model, principal,requestSource, 0);
    }
    @GetMapping("/admin/account/page/{pageNo}")

    public String showAccountPerPage(Model model, Principal principal,
                            @RequestHeader(value = "request-source", required = false) String requestSource,
                            @PathVariable (value = "pageNo") int pageNo) {

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
    @GetMapping("/admin/account/search/{keyword}")

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
    @GetMapping("/admin/account/add")

    public String showAddForm(Model model, Principal principal,
                                     @RequestHeader(value = "request-source", required = false) String requestSource) {
        Account account = new Account();
        account.setAccountId(0);
        model.addAttribute("account", account);

            return "fragments_admin/account-form";
    }
    @PostMapping("/admin/account/update/{accountId}")

    public String showUpateForm(Model model, Principal principal,
                              @RequestHeader(value = "request-source", required = false) String requestSource,
                                @PathVariable(value = "accountId") int accountId) {
        Account account = accountService.getAccountById(accountId);
        model.addAttribute("account", account);

            return "fragments_admin/account-form";
    }
    @PostMapping("/admin/account/delete")

    public String delete(@RequestParam("accountId") int accountId, Model model) {
        accountService.deleteAccountById(accountId);
        Page<Account> pages = accountService.getAccountWithPage(0, 10);
        List<Account> accounts = pages.getContent();

        model.addAttribute("accounts", accounts);
        model.addAttribute("totalPage", pages.getTotalPages());
        model.addAttribute("currentPage", 0);

        return "fragments_admin/account";
    }
    @PostMapping("/admin/account/deleteMany")

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
    @PostMapping("/admin/account/save")
    public String saveAccount(@ModelAttribute("account") Account  account) {
        accountService.saveAccount(account);
        return "redirect:/admin/account";
    }
    @PostMapping("/notebook/update")
    public void updateNotebook(@RequestParam String word, @RequestParam boolean isExist,Principal principal) {
        Account account = accountService.getAccountByUserName(principal.getName());
        if (isExist)
            notebookService.deleteNotebook(account.getAccountId(), word);
        else
            notebookService.addNotebook(account.getAccountId(), word);
    }
    @GetMapping("/lesson")

    public String showLesson(Model model) {
        return "lesson";
    }

//    @GetMapping("/meaning/{word}")
//
//    public String showMeaning(Model model,
//                              Principal principal,
//                              @PathVariable String word,
//                              @RequestHeader(value = "request-source", required = false) String requestSource) {
//
//        Account account = accountService.getAccountByUserName(principal.getName());
//        boolean isExist = notebookService.checkIsExsitInNotebook(account.getAccountId(), word);
//
//        HashMap<String, List<DefinitionDetailView>> results = dictionaryService.getDefinitionByWord(word);
//
//        model.addAttribute("definitions", results);
//        model.addAttribute("word", word.replaceAll("_", " "));
//        model.addAttribute("isExist", isExist);
//
//        if (requestSource == null) {
//            if (principal != null) {
//                model.addAttribute("userData", accountService.getAccountInfoByUsername(principal.getName()));
//            }
//            return "main.html";
//        }
//        else
//            return "fragments/meaning.html";
//    }


    private List<String> StageColors = new ArrayList<>(Arrays.asList(
            "#58cc02",
            "#ce82ff",
            "#00cd9c",
            "#1cb0f6",
            "#ff86d0",
            "#ff9600",
            "#cc348d"
    ));
    @GetMapping("/test")
    public String showTest(Model model) {
        List<Account> accounts = accountService.getAllAccount();
        model.addAttribute("accounts", accounts);
        return "fragments_admin/test";
    }
}
