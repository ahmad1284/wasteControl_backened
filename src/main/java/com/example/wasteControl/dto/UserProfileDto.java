package com.example.wasteControl.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserProfileDto {
    private String userId;
    private MultipartFile profile;
}
