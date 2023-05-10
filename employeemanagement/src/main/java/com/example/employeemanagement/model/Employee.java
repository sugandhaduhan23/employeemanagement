package com.example.employeemanagement.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer employeeId;
    private String empName;
    
    @Column(columnDefinition = "DATE")
    private Date dob;
   
    @Column(columnDefinition = "DATE")
    private Date dateOfJoining;
    
    private String gender;

    @Column(columnDefinition = "LONGBLOB")
    private String photo;

    private BigDecimal salary;
    private String phoneNo;
   
    @Column(unique=true, nullable=false)
    private String emailId;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "roleId")
    private Roles role;
    
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "addressId")
    private Address address;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "departmentId")
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projectId")
    @JsonIncludeProperties({ "projectId" ,"projectCode", "projectName" })
    private Project project;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "managerId")
    @JsonIncludeProperties({ "employeeId", "empName" })
    private Employee manager;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="employeeId")
    @JsonIgnore
    private User user;

    public Employee(String empName, Date dob, Date dateOfJoining, String gender, String photo, BigDecimal salary,
            String phoneNo, String emailId) {
        this.empName = empName;
        this.dob = dob;
        this.dateOfJoining = dateOfJoining;
        this.gender = gender;
        this.photo = photo;
        this.salary = salary;
        this.phoneNo = phoneNo;
        this.emailId = emailId;
    }
}
