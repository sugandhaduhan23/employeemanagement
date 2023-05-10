package com.example.employeemanagement.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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
@Table(name = "project")
public class Project {
 
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer projectId;
    
    @Column(unique=true, nullable=false)
    private String projectCode;
    private String projectName;
    private Integer teamSize;
   
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "managerId")
    @JsonIncludeProperties({ "employeeId", "empName", "emailId", "phoneNo" })
    private Employee projectManager;
    
    @OneToMany(fetch = FetchType.LAZY)
    @JsonIncludeProperties({ "employeeId", "empName", "emailId", "phoneNo"})
    private Set<Employee> teamMembers;

    public Project(String projectCode, String projectName, Integer teamSize) {
        this.projectCode = projectCode;
        this.projectName = projectName;
        this.teamSize = teamSize;
        this.teamMembers = new HashSet<Employee>();
    }

}