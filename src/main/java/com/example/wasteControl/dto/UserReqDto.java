package com.example.wasteControl.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserReqDto {
    private String userName;
    private int status;
    private String password;
    private String firstnme;
    private String surname;
    private String phone;
    private String address;
    private String email;
    private int roleId;
}
