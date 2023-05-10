package com.example.employeemanagement.repositories;

import com.example.employeemanagement.model.Address;

import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Integer> {
    
}
