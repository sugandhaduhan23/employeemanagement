package com.example.employeemanagement.model;

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
@Table(name = "employeeleavebalance")
public class EmployeeLeaveBalance {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="employeeId")
    @JsonIncludeProperties({ "employeeId", "empName" })
    private Employee employee;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="leaveTypeId")
    private LeaveType leave;

    private Integer leaveBalance;

    public EmployeeLeaveBalance(Employee employee, LeaveType leave, Integer leaveBalance) {
        this.employee = employee;
        this.leave = leave;
        this.leaveBalance = leaveBalance;
    }
}
