package com.example.wasteControl.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CurrentTimestamp;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Complain extends Auditable<String>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int complainId;
    private String complainTitle;
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "complainer", referencedColumnName = "userId")
    private User user;
    private String description;
    @CurrentTimestamp
    private LocalDate addedDate;
    private String longitude;
    private String latitude;
    private int status;
    private String image;
    @OneToMany(mappedBy = "complain",fetch = FetchType.LAZY)
    private List<Comments> comments;

}
