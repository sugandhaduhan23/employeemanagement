package com.example.employeemanagement.dto;

import com.example.employeemanagement.model.Employee;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    private Integer userId;
    @JsonIncludeProperties({ "employeeId", "empName", "role", "project", "photo" })
    private Employee employee;
    private String email;
    private boolean firstTimeLogin;
}
