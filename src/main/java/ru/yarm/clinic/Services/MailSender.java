package ru.yarm.clinic.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailSender {

    @Value("${spring.mail.username}")
    private String username;

    @Autowired
    private JavaMailSender mailSender;

    public void send(String clientEmail, String emailSubject, String UUID) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        String message = "Приветствуем вас в нашем интернет-магазине! \n" + "Пройдите по ссылке: <a href=>http://localhost:8080/activation/" + UUID + "</a>";
        mailMessage.setFrom(username);
        mailMessage.setTo(clientEmail);
        mailMessage.setSubject(emailSubject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }


}
