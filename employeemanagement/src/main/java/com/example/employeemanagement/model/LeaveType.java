package com.example.employeemanagement.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "leavetype")
public class LeaveType {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer leaveTypeId;
    private String leaveType;
    private Integer leavesAllowed;
    
    public LeaveType(String leaveType, Integer leavesAllowed) {
        this.leaveType = leaveType;
        this.leavesAllowed = leavesAllowed;
    }

    
}
