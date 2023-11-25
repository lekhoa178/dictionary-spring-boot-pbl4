package com.pbl4.monolingo.service.mailSender;

import com.github.benmanes.caffeine.cache.Cache;
import com.pbl4.monolingo.dao.AccountRepository;
import com.pbl4.monolingo.entity.Account;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    AccountRepository accountRepository;
    @Value("${spring.mail.username}")

    private String username;

    @Value("${spring.mail.password}")

    private String password;
    public boolean sendOTP(String email, HttpServletResponse response){
        Account account = accountRepository.findByEmail(email);
        if (account != null){
            String otp = OtpGenerator.generateOtp();
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Xác thực đổi mật khẩu");
            message.setText("Mã OTP của bạn là " + otp);
            Cookie cookie = new Cookie("otpCookie",otp);
            cookie.setPath("/");
            cookie.setMaxAge(60*2);
            response.addCookie(cookie);
            javaMailSender.send(message);
//            saveOtp(email,otp);
            System.out.println("Gui mail thanh cong");
            return true;
        }
        else {
            System.out.println("Không tìm thấy mail");
            return false;
        }

    }
    @Cacheable(value = "otpCache", key = "{#email}") // Sử dụng email làm key để lưu otp vào cache
    public String saveOtp(String email, String otp ){
        return otp; // Trả về và lưu otp vào cache
    }
    @Cacheable(value = "otpCache", key = "{#email}") // Lấy otp từ cache dựa vào email
    public String getOtpFromCache(String email) {
        return null; // Sẽ không bao giờ được gọi vì giá trị luôn được lấy từ cache
    }
    public boolean verifyOtp(String email, String userOtp,HttpServletRequest request) {
        String cachedOtp = getOtpFromCookie(request); // Lấy otp từ cache
        System.out.println("Otp from cookie: " + cachedOtp);
        return userOtp.equals(cachedOtp); // So sánh otp của người dùng với otp từ cache

    }

    public void sendPassword(String email, String newPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Thay đổi mật khẩu");
        message.setText("Mật khẩu mới của bạn là " + newPassword);
        javaMailSender.send(message);
    }
    private String getOtpFromCookie(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if (cookie.getName().equals("otpCookie")){
                    return cookie.getValue();
                }
            }
        }
        return "";
    }

}
