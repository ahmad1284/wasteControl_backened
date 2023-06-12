package com.example.wasteControl.controller;

import com.example.wasteControl.controller.api.EmailApi;
import com.example.wasteControl.service.EmailService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
@Data
public class EmailController implements EmailApi {

    private final EmailService emailService;

    public ResponseEntity sendEmail(String sendTo) throws MessagingException, jakarta.mail.MessagingException {
        return ResponseEntity.ok().body(emailService.sendEmail(sendTo));

    }


}

