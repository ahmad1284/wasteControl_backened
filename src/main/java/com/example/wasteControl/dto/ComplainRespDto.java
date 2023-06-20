package com.example.wasteControl.dto;

import lombok.Data;

import java.time.LocalDate;


@Data
public class ComplainRespDto {
    private int complainId;
    private String complainTitle;
    private String description;
    private int status;
    private String complainer;
    private LocalDate addedDate;
    private String longitude;
    private String latitude;

    private String image;

}
