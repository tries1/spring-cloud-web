package com.cloud.springcloudweb.repository;

import com.cloud.springcloudweb.model.Customer;

//import org.springframework.data.r2dbc.repository.Query;
//import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import reactor.core.publisher.Flux;

//public interface CustomerRepository extends ReactiveCrudRepository<Customer, Long> {
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    //@Query("SELECT * FROM customer WHERE last_name = :lastname")
    List<Customer> findByLastName(String lastName);
}
