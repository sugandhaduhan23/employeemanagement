package com.example.employeemanagement.repositories;

import com.example.employeemanagement.model.Employee;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Integer>, CustomizedEmployeeRepository{
    
    // @Query(value="SELECT * FROM employee WHERE role_id = 2", nativeQuery = true)
    // Iterable<Employee> findManagers();

    @Query(value="SELECT * FROM employee WHERE project_id = :projectId", nativeQuery = true)
    Iterable<Employee> findEmployeesByProject(int projectId);

    @Query(value="SELECT * FROM employee WHERE role_id = 2 and project_id = :projectId", nativeQuery = true)
    Employee findManagerByProjectCode(int projectId);


}
