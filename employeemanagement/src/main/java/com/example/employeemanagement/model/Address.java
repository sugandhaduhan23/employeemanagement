package com.example.employeemanagement.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer addressId;
    private int streetNo;
    private String streetName;
    private String cityName;
    private String state;
    private String country;
    private int zipCode;

    public Address(int streetNo, String streetName, String cityName, String state, String country, int zipCode) {
        this.streetNo = streetNo;
        this.streetName = streetName;
        this.cityName = cityName;
        this.state = state;
        this.country = country;
        this.zipCode = zipCode;
    }
}