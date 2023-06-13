package com.example.wasteControl.controller.api;


import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
public interface EmailApi {
    @RequestMapping(value = "/email/{sendTo}", method = RequestMethod.POST)
    public ResponseEntity sendEmail(@PathVariable("sendTo")  String sendTo ) throws MessagingException;

}
