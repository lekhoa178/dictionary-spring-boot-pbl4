package com.pbl4.monolingo.controller;

import com.pbl4.monolingo.auth.AuthenticationRequest;
import com.pbl4.monolingo.auth.AuthenticationResponse;
import com.pbl4.monolingo.auth.AuthenticationService;
import com.pbl4.monolingo.auth.RegisterRequest;
import com.pbl4.monolingo.entity.Account;
import com.pbl4.monolingo.entity.DataPerDay;
import com.pbl4.monolingo.rest.BotRestController;
import com.pbl4.monolingo.service.AccountService;
import com.pbl4.monolingo.service.DailyMissionService;
import com.pbl4.monolingo.service.*;
import com.pbl4.monolingo.service.mailSender.MailService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("public")
@RequiredArgsConstructor
public class AuthController {
    private final AccountService accountService;
    private final AuthenticationService authenticationService;
    private final MailService mailSender;
    private HashMap<Integer,Account> currentMapAccount = new HashMap<>();
    private final ExtraInfoService extraInfoService;
    private final DataPerDayService dataPerDayService;
    private final BotRestController botRestController;
    private final DailyMissionService dailyMissionService;
    private final FriendService friendService;
    private String mailCurrent = null;


    @GetMapping("/signup")
    public String showRegisterForm(Model model){
        model.addAttribute("account",new Account());
        return "signUp";
    }
    @PostMapping("/signup")
    public String handleSignUp(@Valid @ModelAttribute("account") Account account,Model model){
        Account checkAccount = accountService.searchAccountByUsername(account.getUsername()).get(0);
        if(checkAccount == null){
            authenticationService.register(RegisterRequest.builder()
                    .username(account.getUsername())
                    .password(account.getPassword())
                    .email(account.getEmail())
                    .build());
            return "redirect:/public/login";
        }
        else {
            model.addAttribute("msg","trùng username, hãy chọn username khác");
            return "signUp";
        }

    }
    @GetMapping("/login")
    public String showLogin(Model model){
        model.addAttribute("account",new Account());
        return "loginPage";
    }
    @PostMapping("/login")
    public String handleLogin(@ModelAttribute("account") Account account, HttpServletResponse response,Model model) throws InterruptedException {
        try {
            AuthenticationResponse authenticationResponse = authenticationService.
                    authenticate(AuthenticationRequest.builder()
                            .username(account.getUsername())
                            .password(account.getPassword()).build());
            String token = authenticationResponse.getToken();
            Cookie cookie = new Cookie("jwtToken",token);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        catch (Exception e){
            model.addAttribute("msg","Tài khoản hoặc mật khẩu sai");
            return "loginPage";
        }


        Account temp = accountService.getAccountByUsername(account.getUsername());
        if(temp.getType().getType().equals("ROLE_ADMIN")){
            authenticationService.getLoginTimes().put(temp.getAccountId(), LocalDateTime.now());
            dailyMissionService.initMission(temp.getAccountId(), 3);
            return "redirect:/admin/account";
        }
        authenticationService.getLoginTimes().put(temp.getAccountId(), LocalDateTime.now());
        botRestController.updateSentences(temp.getAccountId(), 13, false);
        dailyMissionService.initMission(temp.getAccountId(), 3);
        dataPerDayService.updateAccountDPD(temp.getAccountId(),0,0);



        return "redirect:/learn";
    }
    @GetMapping("/error")
    public String showError(){
        return "error";
    }
    @GetMapping("/logout")
    public String signout(HttpServletResponse response, Principal principal){
        try {
            if (principal != null) {
                Account account = accountService.getAccountByUsername(principal.getName());
                LocalDateTime loginTime = authenticationService.getLoginTimes().get(account.getAccountId());
                LocalDateTime logoutTime = LocalDateTime.now();

                float onlineTime = (float) (ChronoUnit.SECONDS.between(loginTime, logoutTime) / 3600.0);

                DataPerDay dt = dataPerDayService.getAccountDPD(account.getAccountId());
                dt.setOnlineHours(dt.getOnlineHours() + onlineTime);

                authenticationService.getLoginTimes().remove(account.getAccountId());
                System.out.println(authenticationService.getLoginTimes().size());
                dataPerDayService.save(dt);
            }
        }
        catch (Exception e) {

        }
        Cookie cookie = new Cookie("jwtToken", null);
        cookie.setPath("/"); // Đảm bảo đường dẫn này phù hợp với đường dẫn khi cookie được tạo
        cookie.setHttpOnly(true); // Tùy chọn, nếu cookie ban đầu được đánh dấu là HttpOnly
        cookie.setMaxAge(0); // Đặt thời gian sống là 0 để xóa cookie
        // Thêm cookie vào response để trình duyệt xóa nó
        response.addCookie(cookie);
        if (principal != null) {
            Account account = accountService.getAccountByUsername(principal.getName());
            account.setOnline(false);
            accountService.saveAccount(account);
        }
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
        List<Account> getAccount = accountService.getAccountByUsernameAndEmail(account.getUsername(),account.getEmail());
        if(getAccount.size() == 0){
            model.addAttribute("msg","Không tìm thấy tài khoản");
            return "ForgotPassword";
        }
        boolean rs = mailSender.sendOTP(account.getEmail(),response);
        if (rs){
            currentMapAccount.put(getAccount.get(0).getAccountId(),getAccount.get(0));
            model.addAttribute("accountId",getAccount.get(0).getAccountId());
            return "VerifyOTP";
        }
        else {
            model.addAttribute("msg","Không tìm thấy tài khoản");
            return "ForgotPassword";
        }

    }

    @PostMapping("/verifyOTP")
    public String checkOTP(@RequestParam("pr1") int num1, @RequestParam("pr2") int num2,
                           @RequestParam("pr3") int num3, @RequestParam("pr4") int num4,
                           @RequestParam("pr5") int num5, @RequestParam("pr6") int num6,
                           @RequestParam("accountId") int accountId,
                           Model model, HttpServletRequest request){
        String otp = num1+""+num2+num3+num4+num5+num6;
        boolean check = mailSender.verifyOtp(currentMapAccount.get(accountId).getEmail(),otp,request);
        if(!check){
            model.addAttribute("msg","Nhập sai otp");
            return "VerifyOTP";
        }
        System.out.println(otp);
        model.addAttribute("accountId",accountId);
        return "ChangePassword";
    }
    @PostMapping("/changePassword")
    public String changePassword(@RequestParam("password")String newPassword,
                                 @RequestParam("accountId") int accountId){
        accountService.changePassword(currentMapAccount.get(accountId),newPassword);
        mailSender.sendPassword(currentMapAccount.get(accountId).getEmail(),newPassword);
        return "redirect:/public/login";
    }

    public String signOut(Principal principal){
        if (principal != null) {
            Account account = accountService.getAccountByUsername(principal.getName());
            LocalDateTime loginTime = authenticationService.getLoginTimes().get(account.getAccountId());
            LocalDateTime logoutTime = LocalDateTime.now();

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
                          HttpServletResponse response,Model model){
        boolean rs = mailSender.sendOTPRegister(mail,response);
        if(rs){
            mailCurrent = mail;
            Account account = new Account();
            account.setEmail(mail);
            model.addAttribute("email",mail);
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
                                 @RequestParam("email")String mail,
                                 Model model, HttpServletRequest request){
        String otp = num1+""+num2+num3+num4+num5+num6;
        boolean check = mailSender.verifyOtpMail(otp,request);
        if(!check){
            model.addAttribute("msg","Nhập sai otp");
            return "non_function/VerifyOTPRegister";
        }
        Account account = new Account();
        account.setEmail(mail);
        model.addAttribute("account",account);
        System.out.println(otp);
        return "signUp";
    }

}
