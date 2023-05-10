package com.example.employeemanagement.exceptions;

public class ProjectMappedException extends RuntimeException {
   public ProjectMappedException(String projectId){
       super("Manager mapped to project: "+ projectId);
   }
}
