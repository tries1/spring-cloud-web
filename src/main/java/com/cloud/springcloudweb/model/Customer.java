package com.cloud.springcloudweb.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@Entity
//@Table(name = "customers")
public class Customer {

    //@Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private final String firstName;

    private final String lastName;

    @Builder
    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return String.format("Customer[id=%d, firstName='%s', lastName='%s']", id, firstName, lastName);
    }
}
