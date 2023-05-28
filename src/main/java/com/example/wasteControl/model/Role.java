package com.example.wasteControl.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roleId;
    private String roleName;
    private int status;
    @OneToMany(mappedBy = "role",fetch = FetchType.LAZY)
    private List<User> userList;


}
