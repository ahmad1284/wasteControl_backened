package com.example.wasteControl.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDate;

@Data
public class ComplainReqDto {
    private String complainTitle;
    private String description;
    private int status;
    private String complainer;
    private String longitude;
    private String latitude;
    private MultipartFile image;

}
