package com.example.employeemanagement.repositories;

import com.example.employeemanagement.model.LeaveType;

import org.springframework.data.repository.CrudRepository;

public interface LeaveRepository extends CrudRepository<LeaveType, Integer>{
    
}
