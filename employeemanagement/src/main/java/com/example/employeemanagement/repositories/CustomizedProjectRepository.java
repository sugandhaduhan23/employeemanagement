package com.example.employeemanagement.repositories;

import java.util.List;

import com.example.employeemanagement.dto.ProjectName;

public interface CustomizedProjectRepository {
    List<ProjectName> getProjectNames();
}
