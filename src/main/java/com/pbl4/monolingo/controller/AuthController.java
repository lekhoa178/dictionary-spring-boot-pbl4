package com.pbl4.monolingo.controller;

import com.pbl4.monolingo.auth.AuthenticationRequest;
import com.pbl4.monolingo.auth.AuthenticationResponse;
import com.pbl4.monolingo.auth.AuthenticationService;
import com.pbl4.monolingo.auth.RegisterRequest;
import com.pbl4.monolingo.entity.Account;
import com.pbl4.monolingo.entity.DataPerDay;
import com.pbl4.monolingo.rest.BotController;
import com.pbl4.monolingo.service.AccountService;
import com.pbl4.monolingo.service.mailSender.MailService;
import com.pbl4.monolingo.service.DataPerDayService;
import com.pbl4.monolingo.service.ExtraInfoService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Controller
@RequestMapping("public")
@RequiredArgsConstructor
public class AuthController {
    private final AccountService accountService;
    private final AuthenticationService authenticationService;
    private final MailService mailSender;
    private Account currentAcount = null;
    private final ExtraInfoService extraInfoService;
    private final DataPerDayService dataPerDayService;
    private final BotController botController;
    private String mailCurrent = null;


    @GetMapping("/signup")
    public String showRegisterForm(Model model){
        model.addAttribute("account",new Account());
        return "signUp";
    }
    @PostMapping("/signup")
    public String handleSignUp(@Valid @ModelAttribute("account") Account account){
        String email = account.getEmail() != null ? account.getEmail() : mailCurrent;
        authenticationService.register(RegisterRequest.builder()
                .username(account.getUsername())
                .password(account.getPassword())
                .email(email)
                .build());
        return "redirect:/public/login";
    }
    @GetMapping("/login")
    public String showLogin(Model model){
        System.out.println("?????");
        model.addAttribute("account",new Account());
        return "loginPage";
    }
    @PostMapping("/login")
    public String handleLogin(@ModelAttribute("account") Account account, HttpSession session, HttpServletResponse response) throws InterruptedException {
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
        botController.getSentences(temp.getAccountId(), 13, null);

        return "redirect:/learn";
    }
    @GetMapping("/error")
    public String showError(){
        return "error";
    }
    @GetMapping("/logout")
    public String signout(HttpServletResponse response){
        Cookie cookie = new Cookie("jwtToken", null);
        cookie.setPath("/"); // Đảm bảo đường dẫn này phù hợp với đường dẫn khi cookie được tạo
        cookie.setHttpOnly(true); // Tùy chọn, nếu cookie ban đầu được đánh dấu là HttpOnly
        cookie.setMaxAge(0); // Đặt thời gian sống là 0 để xóa cookie
        // Thêm cookie vào response để trình duyệt xóa nó
        response.addCookie(cookie);
        System.out.println("Into logout");
        return "redirect:/public/login";
    }
    @GetMapping("/forgot")
    public String forgot(Model model){
        model.addAttribute("account",new Account());
        return "ForgotPassword";
    }
    @PostMapping("/forgot")
    public String sendOTP(@ModelAttribute("account") Account account,Model model,HttpServletResponse response){
        System.out.println("mail: " + account.getEmail());
        Account getAcount = accountService.getAccountByEmail(account.getEmail());
        boolean rs = mailSender.sendOTP(account.getEmail(),response);
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
                           @RequestParam("pr5") int num5, @RequestParam("pr6") int num6,
                           Model model, HttpServletRequest request){
        String otp = num1+""+num2+num3+num4+num5+num6;
        boolean check = mailSender.verifyOtp(currentAcount.getEmail(),otp,request);
        if(!check){
            model.addAttribute("msg","Nhập sai otp");
            return "VerifyOTP";
        }
        System.out.println(otp);
        return "ChangePassword";
    }
    @PostMapping("/changePassword")
    public String changePassword(@RequestParam("password")String newPassword){
        accountService.changePassword(currentAcount,newPassword);
        mailSender.sendPassword(currentAcount.getEmail(),newPassword);
        return "redirect:/public/login";
    }

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
    @GetMapping("/sendOTPRegister")
    public String showSendOtpMail(){
        return "non_function/SendOTPRegister";
    }
    @PostMapping("/sendOTP")
    public String sendOTP(@RequestParam("email") String mail,
                          HttpServletRequest request,
                          HttpServletResponse response){
        boolean rs = mailSender.sendOTPRegister(mail,response);
        if(rs){
            mailCurrent = mail;
//            model.addAttribute("email",mail);
            return "non_function/VerifyOTPRegister";
        }
        else {
            return "non_function/SendOTPRegister";
        }
    }
    @PostMapping("/verifyOTPRegister")
    public String verifyRegister(@RequestParam("pr1") int num1, @RequestParam("pr2") int num2,
                                 @RequestParam("pr3") int num3, @RequestParam("pr4") int num4,
                                 @RequestParam("pr5") int num5, @RequestParam("pr6") int num6,
                                 Model model, HttpServletRequest request){
        String otp = num1+""+num2+num3+num4+num5+num6;
        boolean check = mailSender.verifyOtpMail(otp,request);
        if(!check){
            model.addAttribute("msg","Nhập sai otp");
            return "non_function/VerifyOTPRegister";
        }
        System.out.println(otp);
        return "redirect:/public/signup";
    }

}
