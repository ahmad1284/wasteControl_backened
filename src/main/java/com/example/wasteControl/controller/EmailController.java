package com.example.wasteControl.controller;

import com.example.wasteControl.controller.api.EmailApi;
import com.example.wasteControl.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Data
public class EmailController implements EmailApi {

    private final EmailService emailService;

    public ResponseEntity sendEmail(String sendTo) throws MessagingException {
        return ResponseEntity.ok().body(emailService.sendEmail(sendTo));

    }


}

