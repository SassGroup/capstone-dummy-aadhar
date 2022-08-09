package com.example.Service;


import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Base64;

import java.util.HashMap;
import java.util.Properties;

@Singleton
public class EmaiVarificationByOTP {

//    @Inject
    private JavaMailSender mailSender;

    public EmaiVarificationByOTP() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("korukanti@bluepi.in");
        mailSender.setPassword(new String(Base64.getDecoder().decode("VmluY2VudEAyMDAxMjAwMA==")));

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.debug", "true");

        this.mailSender = mailSender;
    }


    public void EmailVerification(String emailID, String aadharNumber, String otp) {

        String body = "One Time Password (OTP) : " + otp + "\n" + "Your Aadhar Number : " + aadharNumber;

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("korukanti@bluepi.in");
        simpleMailMessage.setTo(emailID);
        simpleMailMessage.setText(body);
        simpleMailMessage.setSubject("OTP Verification...! ");

        mailSender.send(simpleMailMessage);
        System.out.println("Mail Send...!");
    }
}
