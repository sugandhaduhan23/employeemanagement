package com.example.employeemanagement.model;

import java.util.Date;

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
@Table(name = "employeeleaves")
public class EmployeeLeaves {
  
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
   
    private String leaveReason;
    private Date leaveFrom;
    private Date leaveTo;
    private Integer leaveCount;
    private Integer status;

    public EmployeeLeaves(Employee employee, LeaveType leave, String leaveReason, Date leaveFrom, Date leaveTo,
    Integer leaveCount, Integer status) {
            this.employee = employee;
            this.leave = leave;
            this.leaveReason = leaveReason;
            this.leaveFrom = leaveFrom;
            this.leaveTo = leaveTo;
            this.leaveCount = leaveCount;
            this.status = status;
    }
}
