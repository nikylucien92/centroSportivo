package it.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    @Async
    public void sendEmail(String to, String subject, String body) {

        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true); // true = contenuto HTML

            javaMailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException("Errore invio email", e);
        }
    }

//    @Async
//    public void sendEmail(String to, String subject ,String body){
//        SimpleMailMessage email=new SimpleMailMessage();
//        email.setTo(to);
//        email.setSubject(subject);
//        email.setText(body);
//        javaMailSender.send(email);
//    }
}
