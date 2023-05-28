package com.example.wasteControl.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;

@Data
@Entity
public class User extends Auditable<String> implements Serializable {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
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
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "roleId", referencedColumnName = "roleId")
    private Role role;
}
