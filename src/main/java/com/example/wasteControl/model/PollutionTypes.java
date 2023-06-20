package com.example.wasteControl.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class PollutionTypes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String pollutionTypeId;
    private String pollutionTypeName;
    private String description;
}
