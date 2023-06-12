package com.example.wasteControl.service;
import jakarta.mail.internet.MimeMessage;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;

@Service
@Data
public class EmailService {
    private JavaMailSender javaMailSender;
    @Autowired
    public EmailService(JavaMailSender javaMailSender) {

        this.javaMailSender = javaMailSender;
    }

    public Boolean sendEmail(String sendTo) throws MessagingException, jakarta.mail.MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
        } catch (jakarta.mail.MessagingException e) {
            throw new RuntimeException(e);
        }
        String htmlContent = "<h2 style='text-align:center'>You have successfully Joined Waste Control Community</h1>" +
                                "<p>Welcome to Waste Control Portal, You are now a member of the community Please download our mobile app for posting waste complaons " +
                                "in your area and see or track the progress, You can use web for browsing the complains and review their status</p>";
        helper.setTo(sendTo);
        helper.setSubject("Successful Registration to Waste Control Portal");

        helper.setText(htmlContent, true);

        javaMailSender.send(message);

        Map response = new HashMap();
        response.put("response", Boolean.TRUE);
        return Boolean.TRUE;
    }
}
