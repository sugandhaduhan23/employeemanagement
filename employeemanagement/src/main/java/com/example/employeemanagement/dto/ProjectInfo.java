package com.example.employeemanagement.dto;

import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectInfo {
    private Integer projectId;
    private String projectCode;
    private String projectName;
    private Integer teamSize;
    private Integer projectManagerId;
    private Set<Integer> teamMembers = new HashSet<Integer>();
}
