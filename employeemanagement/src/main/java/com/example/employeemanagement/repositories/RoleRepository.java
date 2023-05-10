package com.example.employeemanagement.repositories;

import com.example.employeemanagement.model.Roles;

import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Roles, Integer>{
    
}

