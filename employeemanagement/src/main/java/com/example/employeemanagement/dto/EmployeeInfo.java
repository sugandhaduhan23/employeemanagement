package com.example.employeemanagement.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.example.employeemanagement.model.Address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeInfo {
	private Integer employeeId;
    private String empName;
    private Date dob;
    private Date dateOfJoining;
    private String gender;
    private String photo;
    private BigDecimal salary;
    private String phoneNo;
    private String emailId;
    private Integer roleId;
    private Address address;
    private Integer departmentId;
    private Integer projectId;
    private Integer managerId;
}

