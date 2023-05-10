package com.example.employeemanagement.repositories;

import com.example.employeemanagement.model.Project;

import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<Project, Integer>, CustomizedProjectRepository {
    
      Project findByProjectCode(String projectCode);

}
