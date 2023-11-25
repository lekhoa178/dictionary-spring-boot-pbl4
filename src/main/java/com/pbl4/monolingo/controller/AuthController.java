package com.pbl4.monolingo.controller;

import com.pbl4.monolingo.auth.AuthenticationRequest;
import com.pbl4.monolingo.auth.AuthenticationResponse;
import com.pbl4.monolingo.auth.AuthenticationService;
import com.pbl4.monolingo.auth.RegisterRequest;
import com.pbl4.monolingo.entity.Account;
import com.pbl4.monolingo.service.AccountService;
import com.pbl4.monolingo.service.mailSender.MailService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("public")
@RequiredArgsConstructor
public class AuthController {
    private final AccountService accountService;
    private final AuthenticationService authenticationService;
    private final MailService mailSender;
    private Account currentAcount = null;
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
        return "redirect:/learn";
    }
    @GetMapping("/error")
    public String showError(){
        return "error";
    }
    @GetMapping("/logout")
    public String signout(){
//        session.removeAttribute("jwtToken");
        return "redirect:/public/login";
    }
    @GetMapping("/forgot")
    public String forgot(Model model){
        model.addAttribute("account",new Account());
        return "ForgotPassword";
    }
    @PostMapping("/forgot")
    public String sendOTP(@ModelAttribute("account") Account account,Model model){
        System.out.println("mail: " + account.getEmail());
        Account getAcount = accountService.getAccountByEmail(account.getEmail());
        boolean rs = mailSender.sendOTP(account.getEmail());
        if (rs){
            currentAcount = getAcount;
            return "redirect:/public/verifyOTP";
        }
        else {
            model.addAttribute("msg","Không tìm thấy tài khoản");
            return "ForgotPassword";
        }

    }
    @GetMapping("/verifyOTP")
    public String verifyOTP(){
        return "VerifyOTP";
    }
    @PostMapping("/verifyOTP")
    public String checkOTP(@RequestParam("pr1") int num1, @RequestParam("pr2") int num2,
                           @RequestParam("pr3") int num3, @RequestParam("pr4") int num4,
                           @RequestParam("pr5") int num5, @RequestParam("pr6") int num6){
        String otp = num1+""+num2+num3+num4+num5+num6;
        mailSender.verifyOtp(currentAcount.getEmail(),otp);
        System.out.println(otp);
        return "ChangePassword";
    }
    @PostMapping("/changePassword")
    public String changePassword(@RequestParam("password")String newPassword){
        accountService.changePassword(currentAcount,newPassword);
        return "redirect:/public/login";
    }
}
