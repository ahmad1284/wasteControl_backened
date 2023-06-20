package com.example.wasteControl.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Comments extends Auditable<String>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentId;
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "complainId", referencedColumnName = "complainId")
    private Complain complain;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "commenterId", referencedColumnName = "userId")
    private User user;
    private String comment;
    private int status;
}
