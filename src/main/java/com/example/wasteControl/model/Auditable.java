package com.example.wasteControl.model;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

import static jakarta.persistence.TemporalType.TIMESTAMP;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public abstract class Auditable <U>{
    @CreatedBy
    @Column(updatable = false)
    protected U createdBy;
    @CreatedDate
    @Temporal(TIMESTAMP)
    @Column(updatable = false)
    protected Date createdDate;
    @LastModifiedBy
    protected U modifiedBy;
    @LastModifiedDate
    protected Date modifiedDate;
}
