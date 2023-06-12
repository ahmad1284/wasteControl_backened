package com.example.wasteControl.model;


import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;


@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public abstract class Auditable <U>{
    @CreatedBy
    @Column(updatable = false)
    protected U createdBy;
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    protected Date createdDate;
    @LastModifiedBy
    protected U modifiedBy;
    @LastModifiedDate
    protected Date modifiedDate;


}

