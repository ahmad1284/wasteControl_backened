package com.example.wasteControl.dto;

import com.example.wasteControl.model.Auditable;
import lombok.Data;

@Data
public class UserRespDto extends Auditable<String> {
    private String userId;
    private String userName;
    private int status;
    private String password;
    private String profile;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phone;
    private String address;
    private String email;
}
