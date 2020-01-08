package com.cloud.springcloudweb.domain.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User extends BaseEntity {

    private String name;

    @Builder
    public User(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("User[id=%d, name='%s', createdAt='%s']", id, name, createdAt);
    }
}
