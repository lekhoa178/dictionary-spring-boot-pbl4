package com.pbl4.monolingo.controller;

import com.pbl4.monolingo.entity.Account;
import com.pbl4.monolingo.service.AccountService;
import com.pbl4.monolingo.service.DictionaryService;
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


    @Autowired
    public AdminController(DictionaryService dictionaryController,
                           AccountService accountService) {
        this.dictionaryService = dictionaryController;
        this.accountService = accountService;
    }


    @GetMapping("/account")

    public String showAdmin(Model model, Principal principal,
                            @RequestHeader(value = "request-source", required = false) String requestSource) {
        return showAccountPerPage(model, principal,requestSource, 0);
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

}
