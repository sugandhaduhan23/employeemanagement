package com.example.employeemanagement.controller;

import java.util.logging.Logger;

import com.example.employeemanagement.model.Department;
import com.example.employeemanagement.repositories.DepartmentRepository;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/departments")
public class DepartmentController {

    private static Logger logger = Logger.getLogger(DepartmentController.class.getName());
    private final DepartmentRepository departmentRepository;

     /*************************************************************
     * This values will remain fixed for department table, hence
     * populating the tables only once
     **************************************************************/
    public DepartmentController(DepartmentRepository departmentRepository){
        this.departmentRepository = departmentRepository;
        if(departmentRepository.count() == 0){
            departmentRepository.save(new Department("Applications"));
            departmentRepository.save(new Department("IT Support"));
            departmentRepository.save(new Department("Networking"));
            departmentRepository.save(new Department("Human Resources"));
            departmentRepository.save(new Department("Finance and Admin"));
        }
    }
   
   
    /*************************************************************
     * This API will getch all the departments to populate the
     * dropdowns on frontend.
     **************************************************************/
    @GetMapping
    public Iterable<Department> getDepartments() {
        logger.info("Fetching Departments");
        return departmentRepository.findAll();
    }
}
