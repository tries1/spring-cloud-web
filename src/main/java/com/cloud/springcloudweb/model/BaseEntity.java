package com.cloud.springcloudweb.model;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
//@MappedSuperclass
public class BaseEntity {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    //@Column
    protected LocalDateTime createdAt;

    //@Column
    protected LocalDateTime updatedAt;

    //@Column
    protected LocalDateTime deletedAt;

    //@PostPersist
    protected void onPersist(){
        LocalDateTime now = LocalDateTime.now();

        this.createdAt = now;
        this.updatedAt = now;
    }

    //@PostUpdate
    protected void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }
}
