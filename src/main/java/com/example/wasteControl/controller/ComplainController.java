package com.example.wasteControl.controller;

import com.example.wasteControl.controller.api.ComplainApi;
import com.example.wasteControl.dto.ComplainReqDto;
import com.example.wasteControl.service.ComplainService;
import lombok.Data;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Data
public class ComplainController implements ComplainApi {
    private final ComplainService complainService;
    public ResponseEntity addComplain(ComplainReqDto complainReqDto) {
        return ResponseEntity.ok().body(complainService.add(complainReqDto));
    }
    public ResponseEntity getComplains(int page, int size) {
        return ResponseEntity.ok().body(complainService.getAll(page, size));
    }
    public ResponseEntity getComplainImage(String imageName) throws IOException {
        byte[] imageData = complainService.getComplainImage(imageName);
        return ResponseEntity.status(HttpStatus.OK).contentType(complainService.determineImageMediaType(imageName)).body(imageData);
    }

}
