package com.example.employeemanagement.controller;

import java.util.logging.Logger;

import com.example.employeemanagement.model.Roles;
import com.example.employeemanagement.repositories.RoleRepository;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/roles")
public class RoleController{
    
    private static Logger logger = Logger.getLogger(RoleController.class.getName());
    private final RoleRepository roleRepository;

    /*************************************************************
     * This values will remain fixed for role table, hence
     * populating the tables only once
     **************************************************************/
    public RoleController(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
        if(roleRepository.count() == 0){
            roleRepository.save(new Roles("Admin"));
            roleRepository.save(new Roles("Manager"));
            roleRepository.save(new Roles("Engineer"));
            roleRepository.save(new Roles("HR"));
        }
    }
     
    
    /*************************************************************
     * This API will getch all the roles to populate the
     * dropdowns on frontend.
     **************************************************************/
    @GetMapping
    public Iterable<Roles> getRoles() {
        logger.info("Fetching roles");
        return roleRepository.findAll();
    }
}
