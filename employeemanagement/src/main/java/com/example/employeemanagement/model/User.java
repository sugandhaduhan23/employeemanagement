package com.example.employeemanagement.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer userId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="employeeId")
    @JsonIncludeProperties({ "employeeId", "empName", "role", "project", "photo" })
    private Employee employee;
    
    @Column(unique=true)
    private String email;
    private String password;
   
    @Column(columnDefinition = "TINYINT(1) default true")
    private boolean firstTimeLogin;

    public User(Employee employee, String email, String password, boolean firstTimeLogin) {
        this.employee = employee;
        this.email = email;
        this.password = password;
        this.firstTimeLogin = firstTimeLogin;
    }
}
