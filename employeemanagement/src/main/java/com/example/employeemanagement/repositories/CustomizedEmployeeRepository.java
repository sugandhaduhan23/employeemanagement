package com.example.employeemanagement.repositories;

import java.util.List;

import com.example.employeemanagement.dto.EmployeeName;

public interface CustomizedEmployeeRepository {
    List<EmployeeName> getEmployeeName();
    List<EmployeeName> getEmployeeswithRole(int roleId);
}
