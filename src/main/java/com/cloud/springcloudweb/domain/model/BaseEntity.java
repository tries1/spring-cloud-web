package com.cloud.springcloudweb.domain.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

import lombok.Getter;

@Getter
@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column
    protected LocalDateTime createdAt;

    @Column
    protected LocalDateTime updatedAt;

    @Column
    protected LocalDateTime deletedAt;

    @PostPersist
    protected void onPersist(){
        LocalDateTime now = LocalDateTime.now();

        this.createdAt = now;
        this.updatedAt = now;
    }

    @PostUpdate
    protected void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }
}
