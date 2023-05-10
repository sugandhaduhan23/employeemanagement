package com.example.employeemanagement.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LeaveRequest {
    private Integer id;
    private Integer employeeId;
    private Integer leaveTypeId;
    private String leaveReason;
    private Date leaveFrom;
    private Date leaveTo;
    private Integer leaveCount;
    private Integer status;
}
