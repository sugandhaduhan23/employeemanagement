package com.example.employeemanagement.exceptions;

public class EmployeeNotFoundException  extends RuntimeException{
    public EmployeeNotFoundException(){
        super("Employee/User ID not found");
    }
}

