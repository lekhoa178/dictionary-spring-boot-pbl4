package com.pbl4.monolingo.controller;

import com.pbl4.monolingo.auth.AuthenticationRequest;
import com.pbl4.monolingo.auth.AuthenticationResponse;
import com.pbl4.monolingo.auth.AuthenticationService;
import com.pbl4.monolingo.auth.RegisterRequest;
import com.pbl4.monolingo.entity.Account;
import com.pbl4.monolingo.entity.DataPerDay;
import com.pbl4.monolingo.entity.ExtraInformation;
import com.pbl4.monolingo.service.AccountService;
import com.pbl4.monolingo.service.DataPerDayService;
import com.pbl4.monolingo.service.ExtraInfoService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.http.HttpResponse;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("public")
@RequiredArgsConstructor
public class AuthController {
    private final AccountService accountService;
    private final AuthenticationService authenticationService;
    private final ExtraInfoService extraInfoService;
    private final DataPerDayService dataPerDayService;


    @GetMapping("/signup")
    public String showRegisterForm(Model model){
        model.addAttribute("account",new Account());
        return "signUp";
    }
    @PostMapping("/signup")
    public String handleSignUp(@Valid @ModelAttribute("account") Account account){
        authenticationService.register(RegisterRequest.builder()
                .username(account.getUsername()).password(account.getPassword()).build());
        return "redirect:/public/login";
    }
    @GetMapping("/login")
    public String showLogin(Model model){
        System.out.println("?????");
        model.addAttribute("account",new Account());
        return "loginPage";
    }
    @PostMapping("/login")
    public String handleLogin(@ModelAttribute("account") Account account, HttpSession session, HttpServletResponse response){
        AuthenticationResponse authenticationResponse = authenticationService.
                authenticate(AuthenticationRequest.builder()
                .username(account.getUsername())
                .password(account.getPassword()).build());

        String token = authenticationResponse.getToken();
        Cookie cookie = new Cookie("jwtToken",token);
        cookie.setPath("/");
        response.addCookie(cookie);
        System.out.println("Token from controller: "+ token);

        Account temp = accountService.getAccountByUsername(account.getUsername());
        extraInfoService.updateExtraInfo(temp);
        authenticationService.getLoginTimes().put(temp.getAccountId(), LocalDateTime.now());

        return "redirect:/learn";
    }
    @GetMapping("/error")
    public String showError(){
        return "error";
    }
    @GetMapping("/logout")
    public String signOut(Principal principal){
        if (principal != null) {
            Account account = accountService.getAccountByUsername(principal.getName());
            LocalDateTime loginTime = authenticationService.getLoginTimes().get(account.getAccountId());
            LocalDateTime logoutTime = LocalDateTime.now();

            System.out.println(authenticationService.getLoginTimes().size());
            System.out.println(loginTime + " " + logoutTime);
            float onlineTime = (float) (ChronoUnit.SECONDS.between(loginTime, logoutTime) / 3600.0);

            System.out.println(onlineTime);
            DataPerDay dt = dataPerDayService.getAccountDPD(account.getAccountId());
            dt.setOnlineHours(dt.getOnlineHours() + onlineTime);
            dataPerDayService.save(dt);
        }
        return "redirect:/public/login";
    }
}
