//package com.pbl4.monolingo.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//
//@Configuration
//
//public class MailConfig {
//
//    @Value("${spring.mail.host}")
//
//    private String host;
//
//    @Value("${spring.mail.port}")
//
//    private Integer port;
//
//    @Value("${spring.mail.username}")
//
//    private String username;
//
//    @Value("${spring.mail.password}")
//
//    private String password;
//
//    // Các thuộc tính khác...
//
//    @Bean
//
//    public JavaMailSender mailSender() {
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost(host);
//        mailSender.setPort(port);
//        mailSender.setUsername(username);
//        mailSender.setPassword(password);
//        // Cấu hình thêm các thuộc tính nếu cần
//
//        return mailSender;
//    }
//
//}