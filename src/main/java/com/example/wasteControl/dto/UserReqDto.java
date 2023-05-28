package com.example.wasteControl.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserReqDto {
    private String userName;
    private int status;
    private String password;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phone;
    private String address;
    private String email;

    private int roleId;
}
