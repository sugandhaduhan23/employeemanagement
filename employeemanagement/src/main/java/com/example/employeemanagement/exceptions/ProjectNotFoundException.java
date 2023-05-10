package com.example.employeemanagement.exceptions;

public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException(int projectCode){
        super("Project not found with project code: "+ projectCode);
    }
}
