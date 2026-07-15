package it.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Async
    public void sendEmail(String to, String subject ,String body){
        SimpleMailMessage email=new SimpleMailMessage();
        email.setTo(to);
        email.setSubject(subject);
        email.setText(body);
        javaMailSender.send(email);
    }
}
