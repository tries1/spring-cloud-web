package com.cloud.springcloudweb.repository;

import com.cloud.springcloudweb.model.User;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;

//public interface UserRepository extends JpaRepository<User, Long> {
public interface UserRepository extends ReactiveCrudRepository<User, Long> {
    @Query("select id, name from users u where u.name = :name")
    Flux<User> findByName(String name);
}
