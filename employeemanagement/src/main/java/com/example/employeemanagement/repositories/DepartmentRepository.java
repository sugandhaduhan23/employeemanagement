package com.example.employeemanagement.repositories;

import com.example.employeemanagement.model.Department;

import org.springframework.data.repository.CrudRepository;

public interface DepartmentRepository extends CrudRepository<Department, Integer> {

}
