package com.example.employeemanagement.repositories;

import java.util.List;

import com.example.employeemanagement.dto.EmployeeName;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class CustomizedEmployeeRepositoryImpl implements CustomizedEmployeeRepository{
    
    private final JdbcTemplate jdbc;

    public CustomizedEmployeeRepositoryImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<EmployeeName> getEmployeeName() {
        String sql = "SELECT * FROM employee";

        RowMapper<EmployeeName> rowMapper = (r, i) -> {
            EmployeeName emp = new EmployeeName();
            emp.setEmpId(r.getInt("employee_id"));
            emp.setEmpName(r.getString("emp_name"));
            emp.setProjectId(r.getInt("project_id"));
            return emp;
        };

        return jdbc.query(sql, rowMapper);
    }

    public List<EmployeeName> getEmployeeswithRole(int roleId) {
        String sql = "SELECT * FROM employee where role_id = ?";

        RowMapper<EmployeeName> rowMapper = (r, i) -> {
            EmployeeName emp = new EmployeeName();
            emp.setEmpId(r.getInt("employee_id"));
            emp.setEmpName(r.getString("emp_name"));
            emp.setProjectId(r.getInt("project_id"));
            return emp;
        };

        return jdbc.query(sql, rowMapper, roleId);
    }
    
}
