package com.pbl4.monolingo.service.mailSender;

import org.springframework.stereotype.Service;

import java.util.Random;
@Service
public class OtpGenerator {
    public static String generateOtp() {
        int otpLength = 6; // Độ dài của mã OTP
        String digits = "0123456789"; // Các chữ số để tạo mã OTP
        Random random = new Random();

        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < otpLength; i++) {
            int index = random.nextInt(digits.length());
            char digit = digits.charAt(index);
            otp.append(digit);
        }

        return otp.toString();
    }
}