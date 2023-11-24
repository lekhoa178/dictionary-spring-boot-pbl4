package com.pbl4.monolingo.service.mailSender;

import com.pbl4.monolingo.dao.AccountRepository;
import com.pbl4.monolingo.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    AccountRepository accountRepository;
    @Value("${spring.mail.host}")

    private String host;

    @Value("${spring.mail.port}")

    private Integer port;

    @Value("${spring.mail.username}")

    private String username;

    @Value("${spring.mail.password}")

    private String password;
    public boolean sendOTP(String email){
        Account account = accountRepository.findByEmail(email);
        if (account != null){
            String otp = OtpGenerator.generateOtp();
            SimpleMailMessage message = new SimpleMailMessage();
//            message.setFrom(username);
            message.setTo(email);
            message.setSubject("Xác thực đổi mật khẩu");
            message.setText("Mã OTP của bạn là " + otp);
            javaMailSender.send(message);

            System.out.println("Gui mail thanh cong");
            return true;
        }
        else {
            System.out.println("Không tìm thấy mail");
            return false;
        }

    }
    public void sendPassword(String email, String newPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Thay đổi mật khẩu");
        message.setText("Mật khẩu mới của bạn là " + newPassword);
        javaMailSender.send(message);
    }

}
