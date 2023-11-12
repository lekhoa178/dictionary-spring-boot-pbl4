package com.pbl4.monolingo.controller;

import com.pbl4.monolingo.auth.AuthenticationRequest;
import com.pbl4.monolingo.auth.AuthenticationResponse;
import com.pbl4.monolingo.auth.AuthenticationService;
import com.pbl4.monolingo.auth.RegisterRequest;
import com.pbl4.monolingo.entity.Account;
import com.pbl4.monolingo.service.AccountService;
import jakarta.servlet.http.Cookie;
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

@Controller
@RequestMapping("public")
@RequiredArgsConstructor
public class AuthController {
    private final AccountService accountService;
    private final AuthenticationService authenticationService;
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
        model.addAttribute("account",new Account());
        return "loginPage";
    }
    @PostMapping("login")
    public String handleLogin(@ModelAttribute("account") Account account, HttpSession session, HttpServletResponse response){
        AuthenticationResponse authenticationResponse = authenticationService.
                authenticate(AuthenticationRequest.builder()
                .username(account.getUsername())
                .password(account.getPassword()).build());
        String token = authenticationResponse.getToken();
        Cookie cookie = new Cookie("jwtToken",token);
        cookie.setPath("/");
        cookie.setMaxAge(60*60*24);
        response.addCookie(cookie);
//        session.setAttribute("jwtToken",token);
//        response.setHeader(HttpHeaders.AUTHORIZATION,"Bearer "+ token);
        System.out.println("Token from controller: "+ token);
        return "redirect:/learn";
    }
    @GetMapping("/error")
    public String showError(){
        return "error";
    }
    @GetMapping("/logout")
    public String signout(HttpSession session){
        session.removeAttribute("jwtToken");
        return "redirect:/public/login";
    }
}
